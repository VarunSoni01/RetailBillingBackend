package com.example.billingsoftware.BillingSoftwareBackend.controller;

import com.example.billingsoftware.BillingSoftwareBackend.io.PaymentRequest;
import com.example.billingsoftware.BillingSoftwareBackend.io.RazorpayOrderResponse;
import com.example.billingsoftware.BillingSoftwareBackend.service.OrderService;
import com.example.billingsoftware.BillingSoftwareBackend.service.RazorpayService;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final RazorpayService razorpayService;
    private final OrderService orderService;

    @PostMapping("/create-order")
    @ResponseStatus(HttpStatus.CREATED)
    public RazorpayOrderResponse createRazorpayOrder(@RequestBody PaymentRequest request) throws RazorpayException {
        return razorpayService.createOrder(request.getAmount(), request.getCurrency());
    }

}
