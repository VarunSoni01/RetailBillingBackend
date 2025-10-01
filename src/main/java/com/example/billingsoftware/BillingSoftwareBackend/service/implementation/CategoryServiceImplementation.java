package com.example.billingsoftware.BillingSoftwareBackend.service.implementation;

import com.example.billingsoftware.BillingSoftwareBackend.entity.CategoryEntity;
import com.example.billingsoftware.BillingSoftwareBackend.io.CategoryRequest;
import com.example.billingsoftware.BillingSoftwareBackend.io.CategoryResponse;
import com.example.billingsoftware.BillingSoftwareBackend.repository.CategoryRepository;
import com.example.billingsoftware.BillingSoftwareBackend.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImplementation implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse addCategory(CategoryRequest request) {
        CategoryEntity newCategory = convertToEntity(request);
        newCategory = categoryRepository.save(newCategory);

        return convertToResponse(newCategory);
    }

    private CategoryResponse convertToResponse(CategoryEntity newCategory) {
        return CategoryResponse.builder()
                .categoryId(newCategory.getCategoryUid())
                .name(newCategory.getName())
                .description(newCategory.getDescription())
                .bgColor(newCategory.getBgColor())
                .imageUrl(newCategory.getImageUrl())
                .createdAt(newCategory.getCreatedAt())
                .updatedAt(newCategory.getUpdatedAt())
                .build();
    }

    private CategoryEntity convertToEntity(CategoryRequest request) {
        return CategoryEntity.builder()
                .categoryUid(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .bgColor(request.getBgColor())
                .build();
    }

}
