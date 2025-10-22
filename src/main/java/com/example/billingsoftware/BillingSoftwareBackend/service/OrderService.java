package com.example.billingsoftware.BillingSoftwareBackend.service;

import com.example.billingsoftware.BillingSoftwareBackend.io.OrderRequest;
import com.example.billingsoftware.BillingSoftwareBackend.io.OrderResponse;
import com.example.billingsoftware.BillingSoftwareBackend.io.PaymentVerficationRequest;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderRequest request);

    void deleteOrder(String orderId);

    List<OrderResponse> getLatestOrders();

    OrderResponse verifyPayment(PaymentVerficationRequest request);

    double sumSalesByDate(LocalDate date);

    Long countOrdersByDate(LocalDate date);

    List<OrderResponse> findRecentOrders();
}
