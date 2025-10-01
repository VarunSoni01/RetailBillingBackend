package com.example.billingsoftware.BillingSoftwareBackend.io;

import lombok.Builder;
import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;


@Builder
@Data
public class CategoryResponse {
    private String categoryId;
    private String name;
    private String description;
    private String bgColor;
    private String imageUrl;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
