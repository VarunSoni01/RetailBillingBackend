package com.example.billingsoftware.BillingSoftwareBackend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

    String uploadFile(MultipartFile file);

    Boolean deleteFile(String imageUrl);

}
