package com.ivon.purba.service.serviceInterface;

import com.ivon.purba.domain.ContentType;
import com.ivon.purba.domain.Photo;
import com.ivon.purba.domain.User;
import com.ivon.purba.exception.AIAnalysisException;
import com.ivon.purba.exception.PhotoSaveException;
import com.ivon.purba.exception.ResourceNotFoundException;

public interface PhotoService {
    Photo save(Photo photo) throws PhotoSaveException;

    Photo createPhoto(User user, String photoUrl, ContentType contentType);

    Photo findById(Long id) throws ResourceNotFoundException;
    Photo analyzeAndSavePhotoDetails(String filePath, Photo photo) throws AIAnalysisException;
}
