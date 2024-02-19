package com.ivon.purba.web;

import com.ivon.purba.domain.ContentType;
import com.ivon.purba.domain.Photo;
import com.ivon.purba.domain.User;
import com.ivon.purba.dto.aiService.PhotoAnalysisResponse;
import com.ivon.purba.dto.photoController.PhotoUploadRequest;
import com.ivon.purba.dto.photoController.PhotoUploadResponse;
import com.ivon.purba.exception.AIAnalysisException;
import com.ivon.purba.exception.PhotoSaveException;
import com.ivon.purba.exception.ResourceNotFoundException;
import com.ivon.purba.service.AiServiceImpl;
import com.ivon.purba.service.ContentTypeService;
import com.ivon.purba.service.PhotoService;
import com.ivon.purba.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;
    private final UserService userService;
    private final ContentTypeService contentTypeService;
    private final AiServiceImpl aiService;

//    @GetMapping("/photo/{userId}/getPhotos")
//    public ResponseEntity<List<Photo>> getAllPhotosByUserId(@PathVariable("userId") Long userId) {
//        List<Photo> photos = photoService.findAllPhotoByUserId(userId);
//        if (photos.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.ok(photos);
//    }
    @PostMapping(value = "/photo/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadPhoto(@ModelAttribute PhotoUploadRequest photoUploadRequest) {
        try {
            User user = userService.findById(photoUploadRequest.getUserId());
            ContentType contentType = contentTypeService.getContentType("image");


            MultipartFile file = photoUploadRequest.getFile();

            String fileName = file.getOriginalFilename();
            String fileExtension = fileName.substring(fileName.lastIndexOf("."));
            String storedFileName = UUID.randomUUID().toString() + fileExtension;

            String uploadDir = "C:/Users/dydal/OneDrive/Desktop/images";
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File storedFile = new File(uploadDir, storedFileName);
            file.transferTo(storedFile);

            Photo photo = new Photo();
            photo.setUser(user);
            photo.setPhotoUrl(storedFileName);
            photo.setContentType(contentType);

            Photo savedPhoto = photoService.save(photo);

            PhotoAnalysisResponse analysisResult = aiService.analyzePhoto(savedPhoto.getPhotoUrl());
            savedPhoto.setTitle(analysisResult.getTitle());
            savedPhoto.setSummary(analysisResult.getSummary());
            photoService.save(savedPhoto);

            PhotoUploadResponse response = new PhotoUploadResponse();
            response.setPhotoId(savedPhoto.getContentId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (PhotoSaveException | AIAnalysisException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
