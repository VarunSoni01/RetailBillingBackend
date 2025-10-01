package com.example.billingsoftware.BillingSoftwareBackend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tbl_category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String categoryUid;
    @Column(unique = true)
    private String name;
    private String description;
    private String bgColor;
    private String imageUrl;
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;
}
