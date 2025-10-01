package com.example.billingsoftware.BillingSoftwareBackend.service;

import com.example.billingsoftware.BillingSoftwareBackend.io.CategoryRequest;
import com.example.billingsoftware.BillingSoftwareBackend.io.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse addCategory(CategoryRequest request);

    List<CategoryResponse> readCategories();

    CategoryResponse updateCategory(CategoryResponse category);

    void deleteCategory(String categoryId);
}
