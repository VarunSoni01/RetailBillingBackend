package com.example.billingsoftware.BillingSoftwareBackend.repository;

import com.example.billingsoftware.BillingSoftwareBackend.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {
}
