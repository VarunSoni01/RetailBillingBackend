package com.example.billingsoftware.BillingSoftwareBackend.io;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemResponse
{
    private String itemId;
    private String name;
    private BigDecimal price;
    private String description;
    private String imgUrl;
    private String categoryId;
    private String categoryName;
    private Timestamp createdAt;
    private Timestamp updatedAt;


}
