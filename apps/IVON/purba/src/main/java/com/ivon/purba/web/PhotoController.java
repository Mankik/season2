package com.ivon.purba.web;

import com.ivon.purba.domain.ContentType;
import com.ivon.purba.domain.Photo;
import com.ivon.purba.domain.User;
import com.ivon.purba.dto.photoController.PhotoUploadRequest;
import com.ivon.purba.dto.photoController.PhotoUploadResponse;
import com.ivon.purba.exception.AIAnalysisException;
import com.ivon.purba.exception.PhotoSaveException;
import com.ivon.purba.exception.ResourceNotFoundException;
import com.ivon.purba.service.AiServiceImpl;
import com.ivon.purba.service.ContentTypeService;
import com.ivon.purba.service.FileServiceImpl;
import com.ivon.purba.service.serviceInterface.PhotoService;
import com.ivon.purba.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;
    private final UserService userService;
    private final ContentTypeService contentTypeService;
    private final FileServiceImpl fileService;

    @PostMapping(value = "/photo/ai-analysis", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadPhoto(@ModelAttribute PhotoUploadRequest photoUploadRequest) {
        try {
            User user = userService.findById(photoUploadRequest.getUserId());
            ContentType contentType = contentTypeService.getContentType("image");

            MultipartFile file = photoUploadRequest.getFile();

            String storedFileName = fileService.storeFile(file);

            Photo photo = photoService.createPhoto(user, storedFileName, contentType);
            Photo savedPhoto = photoService.save(photo);

            savedPhoto = photoService.analyzeAndSavePhotoDetails(storedFileName, savedPhoto);

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
