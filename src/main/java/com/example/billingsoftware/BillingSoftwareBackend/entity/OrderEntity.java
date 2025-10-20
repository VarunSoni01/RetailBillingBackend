package com.example.billingsoftware.BillingSoftwareBackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderId;
    private String customerName;
    private String mobileNumber;
    private Double subTotal;
    private Double grandTotal;
    private Double tax;
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate(){
        this.orderId = "ORD_"+System.currentTimeMillis();
        this.createdAt = LocalDateTime.now();
    }

}
