package com.example.billingsoftware.BillingSoftwareBackend.controller;

import com.example.billingsoftware.BillingSoftwareBackend.io.DashboardResponse;
import com.example.billingsoftware.BillingSoftwareBackend.io.OrderResponse;
import com.example.billingsoftware.BillingSoftwareBackend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {

    private final OrderService orderService;

    @GetMapping
    public DashboardResponse getDashboardData(){
        LocalDate today = LocalDate.now();

        Double todaySales = orderService.sumSalesByDate(today);
        Long todayOrderCount = orderService.countOrdersByDate(today);
        List<OrderResponse> recentOrder = orderService.findRecentOrders();

        return new DashboardResponse(
                todaySales != null ? todaySales : 0.0,
                todayOrderCount != null ? todayOrderCount.longValue() : 0L,
                recentOrder
        );
    }




}
