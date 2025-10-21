package com.example.billingsoftware.BillingSoftwareBackend.service;

import com.example.billingsoftware.BillingSoftwareBackend.io.OrderRequest;
import com.example.billingsoftware.BillingSoftwareBackend.io.OrderResponse;
import com.example.billingsoftware.BillingSoftwareBackend.io.PaymentVerficationRequest;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderRequest request);

    void deleteOrder(String orderId);

    List<OrderResponse> getLatestOrders();

    OrderResponse verifyPayment(PaymentVerficationRequest request);
}
