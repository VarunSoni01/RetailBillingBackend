package com.example.billingsoftware.BillingSoftwareBackend.controller;

import com.example.billingsoftware.BillingSoftwareBackend.io.ItemRequest;
import com.example.billingsoftware.BillingSoftwareBackend.io.ItemResponse;
import com.example.billingsoftware.BillingSoftwareBackend.service.ItemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/admin/addItem")
    public ItemResponse addItem(@RequestPart("item")String itemString, @RequestPart("file") MultipartFile image){
        ObjectMapper objectMapper = new ObjectMapper();
        ItemRequest itemRequest = null;
        try{
            itemRequest = objectMapper.readValue(itemString, ItemRequest.class);

            return itemService.add(itemRequest, image);
        }catch (JsonProcessingException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error occured while converting json");
        }
    }

    @GetMapping("/items")
    public List<ItemResponse> readItems(){
        return itemService.fetchItems();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("admin/item/delete/{itemId}")
    public void deleteItem(@PathVariable String itemId){
        try{
            itemService.deleteItem(itemId);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
        }
    }

}
