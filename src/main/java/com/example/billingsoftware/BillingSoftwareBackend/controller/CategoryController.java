package com.example.billingsoftware.BillingSoftwareBackend.controller;

import com.example.billingsoftware.BillingSoftwareBackend.io.CategoryRequest;
import com.example.billingsoftware.BillingSoftwareBackend.io.CategoryResponse;
import com.example.billingsoftware.BillingSoftwareBackend.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/addCategory")
    @ResponseStatus(HttpStatus.CREATED)
//    public CategoryResponse addCategory(@RequestBody CategoryRequest request){
    public CategoryResponse addCategory(@RequestPart("category")String categoryString, @RequestPart("file") MultipartFile file){
        CategoryRequest request = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            request = objectMapper.readValue(categoryString, CategoryRequest.class);
            return categoryService.addCategory(request,file);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Exception occured while paring object to json - "+e.getMessage());
        }
    }


    @GetMapping
    public List<CategoryResponse> getCategories(){
        return categoryService.readCategories();
    }

    @DeleteMapping("deleteCategory/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable String categoryId){
        try{
            categoryService.deleteCategory(categoryId);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
