package com.example.billingsoftware.BillingSoftwareBackend.service.implementation;


import com.example.billingsoftware.BillingSoftwareBackend.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadImplementation implements FileUploadService {

    private final String UPLOAD_DIR_PATH = System.getProperty("user.dir")+"/uploads";

    @Override
    public String uploadFile(MultipartFile file) {
        String fileNameExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
        String key = UUID.randomUUID().toString()+"."+fileNameExtension;

        try{
            Path uploadPath = Paths.get(UPLOAD_DIR_PATH);
            if(!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }

//            Saving the file on directory path
            Path filePath = uploadPath.resolve(key);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(key)
                    .toUriString();

            return fileUrl;

//            return filePath.toString();
        }catch (Exception e){
            throw new RuntimeException("Error while uploading files: "+e.getMessage());
        }

    }

    @Override
    public Boolean deleteFile(String imageUrl) {
        try{
            String fileName = imageUrl.substring(imageUrl.lastIndexOf('/')+1);

            Path uploadPath = Paths.get(UPLOAD_DIR_PATH).toAbsolutePath().normalize();
            Path filePath = uploadPath.resolve(fileName).toAbsolutePath().normalize();

            // Prevent path traversal: ensure the resolved file is inside the upload directory
            if (!filePath.startsWith(uploadPath)) {
                throw new RuntimeException("Invalid file path");
            }

            return Files.deleteIfExists(filePath);

        }catch (Exception e){
            throw new RuntimeException("Error deleting file: "+e.getMessage());
        }
    }
}
