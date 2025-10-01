package com.example.billingsoftware.BillingSoftwareBackend.repository;

import com.example.billingsoftware.BillingSoftwareBackend.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {

    Optional<CategoryEntity> findByCategoryUid(String categoryId);
}
