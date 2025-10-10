package com.example.billingsoftware.BillingSoftwareBackend.service.implementation;

import com.example.billingsoftware.BillingSoftwareBackend.entity.CategoryEntity;
import com.example.billingsoftware.BillingSoftwareBackend.entity.ItemEntity;
import com.example.billingsoftware.BillingSoftwareBackend.io.ItemRequest;
import com.example.billingsoftware.BillingSoftwareBackend.io.ItemResponse;
import com.example.billingsoftware.BillingSoftwareBackend.repository.CategoryRepository;
import com.example.billingsoftware.BillingSoftwareBackend.repository.ItemRepository;
import com.example.billingsoftware.BillingSoftwareBackend.service.FileUploadService;
import com.example.billingsoftware.BillingSoftwareBackend.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {


    private final ItemRepository itemRepository;
    private final FileUploadService fileUploadService;
    private final CategoryRepository categoryRepository;

    @Override
    public ItemResponse add(ItemRequest request, MultipartFile file) {
        String url = fileUploadService.uploadFile(file);
        ItemEntity newItem = convertToEntity(request);
        CategoryEntity category = categoryRepository.findByCategoryUid(request.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found"));
        newItem.setCategory(category);
        newItem.setImgUrl(url);
        newItem = itemRepository.save(newItem);
        return convertToResponse(newItem);
    }

    private ItemResponse convertToResponse(ItemEntity newItem) {
        return ItemResponse.builder()
                .itemId(newItem.getItemId())
                .name(newItem.getName())
                .description(newItem.getDescription())
                .price(newItem.getPrice())
                .imgUrl(newItem.getImgUrl())
                .categoryName(newItem.getCategory().getName())
                .categoryId(newItem.getCategory().getCategoryUid())
                .createdAt(newItem.getCreatedAt())
                .updatedAt(newItem.getUpdatedAt())
                .build();
    }

    private ItemEntity convertToEntity(ItemRequest request) {
        return ItemEntity.builder()
                .itemId(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();
    }

    @Override
    public List<ItemResponse> fetchItems() {
        return itemRepository.findAll()
                .stream()
                .map(itemEntity -> convertToResponse(itemEntity))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteItem(String itemId) {
        ItemEntity existingItem = itemRepository.findByItemId(itemId).orElseThrow(() -> new RuntimeException("Item not found"));
        boolean isFileDeleted = fileUploadService.deleteFile(existingItem.getImgUrl());

        if(isFileDeleted){
            itemRepository.delete(existingItem);
        }else{
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to delete the image");
        }
    }
}
