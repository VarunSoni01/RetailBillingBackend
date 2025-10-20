package com.example.billingsoftware.BillingSoftwareBackend.entity;

import com.example.billingsoftware.BillingSoftwareBackend.io.PaymentDetails;
import com.example.billingsoftware.BillingSoftwareBackend.io.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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


    // Define one-to-many relationship with OrderItemEntity
    // An order can have multiple order items
    // CascadeType.ALL ensures that all operations (persist, merge, remove, etc.) are cascaded to the order items
    // orphanRemoval = true ensures that if an order item is removed from the order, it is also removed from the database
    // Explain cascade and orphanRemoval in comments
    // CascadeType.ALL means that any operation performed on the parent entity (OrderEntity) will be cascaded to the child entities (OrderItemEntity)
    // For example, if an OrderEntity is deleted, all associated OrderItemEntity instances will also be deleted
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) //write comment here
    @JoinColumn(name = "order_id")
    private List<OrderItemEntity> items = new ArrayList<>();

    @Embedded
    private PaymentDetails paymentDetails;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @PrePersist
    protected void onCreate(){
        this.orderId = "ORD_"+System.currentTimeMillis();
        this.createdAt = LocalDateTime.now();
    }

}
