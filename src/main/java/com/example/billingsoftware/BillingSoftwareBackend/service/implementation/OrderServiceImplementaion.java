package com.example.billingsoftware.BillingSoftwareBackend.service.implementation;

import com.example.billingsoftware.BillingSoftwareBackend.entity.OrderEntity;
import com.example.billingsoftware.BillingSoftwareBackend.entity.OrderItemEntity;
import com.example.billingsoftware.BillingSoftwareBackend.io.*;
import com.example.billingsoftware.BillingSoftwareBackend.repository.OrderEntityRepository;
import com.example.billingsoftware.BillingSoftwareBackend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImplementaion implements OrderService {

    private final OrderEntityRepository orderEntityRepository;

    @Override
    public OrderResponse createOrder(OrderRequest request) {
        OrderEntity newOrder = converToOrderEntity(request);
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setPaymentStatus(newOrder.getPaymentMethod() == PaymentMethod.CASH ? PaymentDetails.PaymentStatus.COMPLETED : PaymentDetails.PaymentStatus.PENDING);
        newOrder.setPaymentDetails(paymentDetails);
        List<OrderItemEntity> orderItems = request.getCartItems().stream()
                .map(this::converToOrderItemEntity)
                .collect(Collectors.toList());

        newOrder.setItems(orderItems);

        newOrder = orderEntityRepository.save(newOrder);
        return convertToResponse(newOrder);
    }

    private OrderItemEntity converToOrderItemEntity(OrderRequest.OrderItemRequest orderItemRequest) {
        return OrderItemEntity.builder()
                .itemId(orderItemRequest.getItemId())
                .name(orderItemRequest.getName())
                .price(orderItemRequest.getPrice())
                .quantity(orderItemRequest.getQuantity())
                .build();
    }

    private OrderResponse convertToResponse(OrderEntity newOrder) {
        return OrderResponse.builder()
                .orderId(newOrder.getOrderId())
                .customerName(newOrder.getCustomerName())
                .phoneNumber(newOrder.getPhoneNumber())
                .subTotal(newOrder.getSubTotal())
                .tax(newOrder.getTax())
                .grandTotal(newOrder.getGrandTotal())
                .paymentMethod(newOrder.getPaymentMethod())
                .items(newOrder.getItems().stream()
                        .map(this::convertToItemResponse)
                        .collect(Collectors.toList()))
                .paymentDetails(newOrder.getPaymentDetails())
                .createdAt(newOrder.getCreatedAt())
                .build();
    }

    private OrderResponse.OrderItemResponse convertToItemResponse(OrderItemEntity orderItemEntity) {
        return OrderResponse.OrderItemResponse.builder()
                .itemId(orderItemEntity.getItemId())
                .name(orderItemEntity.getName())
                .price(orderItemEntity.getPrice())
                .quantity(orderItemEntity.getQuantity())
                .build();
    }

    private OrderEntity converToOrderEntity(OrderRequest request) {
        return OrderEntity.builder()
                .customerName(request.getCustomerName())
                .phoneNumber(request.getPhoneNumber())
                .subTotal(request.getSubTotal())
                .tax(request.getTax())
                .grandTotal(request.getGrandTotal())
                .paymentMethod(PaymentMethod.valueOf(request.getPaymentMethod()))
                .build();
    }


    @Override
    public void deleteOrder(String orderId) {
        OrderEntity existingOrder = orderEntityRepository.findByOrderId(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        orderEntityRepository.delete(existingOrder);
    }

    @Override
    public List<OrderResponse> getLatestOrders() {
        return orderEntityRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse verifyPayment(PaymentVerficationRequest request) {
        OrderEntity existingOrder = orderEntityRepository.findByOrderId(request.getOrderId()).orElseThrow(() -> new RuntimeException("Order not found"));
        if(!verifyRazorpaySignature(request.getRazorpayOrderId(), request.getRazorpayPaymentId(),request.getRazorpaySignature())){
            throw new RuntimeException("Payment verification failed");
        }

        PaymentDetails paymentDetails = existingOrder.getPaymentDetails();
        paymentDetails.setRazorpayPaymentId(request.getRazorpayPaymentId());
        paymentDetails.setRazorpayOrderId(request.getRazorpayOrderId());
        paymentDetails.setRazorpaySignature(request.getRazorpaySignature());
        paymentDetails.setPaymentStatus(PaymentDetails.PaymentStatus.COMPLETED);

        existingOrder = orderEntityRepository.save(existingOrder);
        return convertToResponse(existingOrder);
    }

    @Override
    public double sumSalesByDate(LocalDate date) {
        Double total = orderEntityRepository.sumSalesByDate(date);
        return total != null ? total : 0.0;

    }

    @Override
    public Long countOrdersByDate(LocalDate date) {
        return orderEntityRepository.countByOrderDate(date);
    }

    @Override
    public List<OrderResponse> findRecentOrders() {
        return orderEntityRepository.findRecentOrders()
                .stream()
                .map(orderEntity -> convertToResponse(orderEntity))
                .collect(Collectors.toList());
    }

    private boolean verifyRazorpaySignature(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature) {
        return true; //for testing
    }
}
