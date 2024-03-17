package com.ivon.purba.service;

import com.ivon.purba.exception.InvalidFileNameException;
import com.ivon.purba.exception.FileStorageException;
import com.ivon.purba.service.serviceInterface.FileService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Getter
@Setter
public class FileServiceImpl implements FileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public String storeFile(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("업로드된 파일이 없습니다.");
            }

            String fileName = generateUniqueFileName(file);
            Path targetLocation = Paths.get(uploadDir).resolve(fileName);
            Files.createDirectories(targetLocation.getParent());
            file.transferTo(targetLocation.toFile());

            return targetLocation.toString();
        } catch (IOException e) {
            throw new FileStorageException("파일을 저장하는 도중 오류가 발생했습니다." + e.getMessage());
        }
    }

    private String generateUniqueFileName(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || originalFileName.isEmpty()) {
            throw new InvalidFileNameException("파일 이름이 올바르지 않습니다.");
        }

        String fileExtension = getFileExtension(originalFileName);
        return UUID.randomUUID().toString() + fileExtension;
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1 || dotIndex == fileName.length() - 1) {
            throw new InvalidFileNameException("파일 이름에 확장자가 없습니다.");
        }
        return fileName.substring(dotIndex);
    }
}

