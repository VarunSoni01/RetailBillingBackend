package com.example.billingsoftware.BillingSoftwareBackend.io;

import lombok.Builder;
import lombok.Data;

@Builder //builder is used to create object of this class without construction using builder mehthod and setter - getter
@Data //Creates getter, setter, required args constructore, toString, EqualsAndHashCode functions
public class CategoryRequest {
    private String name;
    private String description;
    private String bgColor;
}
