package com.ivon.purba.web;

import com.ivon.purba.domain.ContentType;
import com.ivon.purba.domain.Photo;
import com.ivon.purba.domain.User;
import com.ivon.purba.dto.photoController.PhotoUploadRequest;
import com.ivon.purba.dto.photoController.PhotoUploadResponse;
import com.ivon.purba.service.ContentTypeServiceImpl;
import com.ivon.purba.service.FileServiceImpl;
import com.ivon.purba.service.UserServiceImpl;
import com.ivon.purba.service.serviceInterface.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;
    private final UserServiceImpl userService;
    private final ContentTypeServiceImpl contentTypeService;
    private final FileServiceImpl fileService;

    //사진 업로드 및 AI 분석
    @PostMapping(value = "/photo/ai-analysis", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadPhoto(@ModelAttribute PhotoUploadRequest photoUploadRequest) {
        User user = userService.getUserById(photoUploadRequest.getUserId());
        ContentType contentType = contentTypeService.getContentType("image");

        MultipartFile file = photoUploadRequest.getFile();

        String storedFileName = fileService.storeFile(file);

        Photo photo = photoService.createPhoto(user, storedFileName, contentType);
        Photo savedPhoto = photoService.save(photo);

        savedPhoto = photoService.analyzeAndSavePhotoDetails(storedFileName, savedPhoto);

        PhotoUploadResponse response = new PhotoUploadResponse(contentType.getName(), savedPhoto.getTitle(), savedPhoto.getSummary(), savedPhoto.getLocation(), savedPhoto.getStartDate(), savedPhoto.getEndDate(), savedPhoto.getCharge(), savedPhoto.getBankAccount());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
