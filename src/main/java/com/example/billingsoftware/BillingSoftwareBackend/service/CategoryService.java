package com.example.billingsoftware.BillingSoftwareBackend.service;

import com.example.billingsoftware.BillingSoftwareBackend.io.CategoryRequest;
import com.example.billingsoftware.BillingSoftwareBackend.io.CategoryResponse;

public interface CategoryService {

    CategoryResponse addCategory(CategoryRequest request);
}
