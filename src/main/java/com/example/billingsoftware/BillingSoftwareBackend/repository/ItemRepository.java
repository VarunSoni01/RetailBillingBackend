package com.example.billingsoftware.BillingSoftwareBackend.repository;

import com.example.billingsoftware.BillingSoftwareBackend.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    Optional<ItemEntity> findByItemId(String id);

    Integer countByCategoryId(String id);
}
