package com.example.billingsoftware.BillingSoftwareBackend.service.implementation;

import com.example.billingsoftware.BillingSoftwareBackend.entity.OrderEntity;
import com.example.billingsoftware.BillingSoftwareBackend.entity.OrderItemEntity;
import com.example.billingsoftware.BillingSoftwareBackend.io.OrderRequest;
import com.example.billingsoftware.BillingSoftwareBackend.io.OrderResponse;
import com.example.billingsoftware.BillingSoftwareBackend.io.PaymentDetails;
import com.example.billingsoftware.BillingSoftwareBackend.io.PaymentMethod;
import com.example.billingsoftware.BillingSoftwareBackend.repository.OrderEntityRepository;
import com.example.billingsoftware.BillingSoftwareBackend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImplementaion implements OrderService {

    private final OrderEntityRepository orderEntityRepository;

    @Override
    public OrderRequest createOrder(OrderRequest request) {
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




    @Override
    public void deleteOrder(String orderId) {

    }

    @Override
    public List<OrderResponse> getLatestOrders() {
        return List.of();
    }
}
