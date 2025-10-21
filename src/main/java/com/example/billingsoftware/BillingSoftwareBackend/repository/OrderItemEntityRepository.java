package com.example.billingsoftware.BillingSoftwareBackend.repository;

import com.example.billingsoftware.BillingSoftwareBackend.entity.OrderEntity;
import com.example.billingsoftware.BillingSoftwareBackend.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemEntityRepository extends JpaRepository<OrderItemEntity, Long> {

}
