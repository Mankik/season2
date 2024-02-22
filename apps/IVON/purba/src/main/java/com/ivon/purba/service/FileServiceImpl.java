package com.ivon.purba.service;

import com.ivon.purba.service.serviceInterface.FileService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Getter
@Setter
public class FileServiceImpl implements FileService {

    private final String uploadDir = "C:/Users/dydal/OneDrive/Desktop/images";
    private String fileName;
    @Override
    public String storeFile(MultipartFile file) throws IOException {
        fileName = generateUniqueFileName(file);
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File storedFile = new File(uploadDir, fileName);
        file.transferTo(storedFile);

        String filePath = storedFile.getAbsolutePath();
        return filePath;
    }

    private String generateUniqueFileName(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        return UUID.randomUUID().toString() + fileExtension;
    }
}

