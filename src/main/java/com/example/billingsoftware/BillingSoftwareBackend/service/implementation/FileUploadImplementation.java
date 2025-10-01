package com.example.billingsoftware.BillingSoftwareBackend.service.implementation;


import com.example.billingsoftware.BillingSoftwareBackend.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

            return filePath.toString();
        }catch (Exception e){
            throw new RuntimeException("Error while uploading files: "+e.getMessage());
        }

    }

    @Override
    public Boolean deleteFile(String imageUrl) {
        try{
            Path path = Paths.get(imageUrl);
            return Files.deleteIfExists(path);
        }catch (Exception e){
            throw new RuntimeException("Error deleting file: "+e.getMessage());
        }
    }
}
