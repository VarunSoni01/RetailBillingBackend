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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Override
    public List<CategoryResponse> readCategories() {

        return categoryRepository.findAll().stream()
                .map(categoryEntity -> convertToResponse(categoryEntity))
                .collect(Collectors.toList());

//        Or

//        List<CategoryEntity> categories = categoryRepository.findAll();
//        List<CategoryResponse> categoryResponses = new ArrayList<>();
//        for(CategoryEntity category : categories){
//            categoryResponses.add(convertToResponse(category));
//        }
//
//        return categoryResponses;
    }

    @Override
    public CategoryResponse updateCategory(CategoryResponse category) {
        return null;
    }

    @Override
    public void deleteCategory(String categoryId) {
        CategoryEntity existingCategory = categoryRepository.findByCategoryUid(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with Id - "+categoryId));

        categoryRepository.delete(existingCategory);
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
