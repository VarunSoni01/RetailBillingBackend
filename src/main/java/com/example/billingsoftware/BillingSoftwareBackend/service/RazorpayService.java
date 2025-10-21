package com.example.billingsoftware.BillingSoftwareBackend.service;

import com.example.billingsoftware.BillingSoftwareBackend.io.RazorpayOrderResponse;
import com.razorpay.RazorpayException;

public interface RazorpayService {

    RazorpayOrderResponse createOrder(Double amount, String currency) throws RazorpayException;
}
