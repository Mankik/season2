package com.ivon.purba.service;

import com.ivon.purba.domain.Content;
import com.ivon.purba.domain.ContentType;
import com.ivon.purba.domain.Photo;
import com.ivon.purba.domain.User;
import com.ivon.purba.dto.aiService.PhotoAnalysisResponse;
import com.ivon.purba.exception.AIAnalysisException;
import com.ivon.purba.exception.PhotoSaveException;
import com.ivon.purba.exception.ResourceNotFoundException;
import com.ivon.purba.repository.PhotoRepository;
import com.ivon.purba.service.serviceInterface.AiService;
import com.ivon.purba.service.serviceInterface.FileService;
import com.ivon.purba.service.serviceInterface.PhotoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;
    private final AiService aiService;

    @Autowired
    public PhotoServiceImpl(PhotoRepository photoRepository, AiService aiService, FileService fileService) {
        this.photoRepository = photoRepository;
        this.aiService = aiService;
    }

    @Override
    public Photo save(Photo photo) {
        try {
            return photoRepository.save(photo);
        } catch (DataAccessException e) {
            throw new PhotoSaveException();
        }
    }

    @Override
    public Photo createPhoto(User user, String photoUrl, ContentType contentType) {
        Photo photo = new Photo();
        photo.setUser(user);
        photo.setUrl(photoUrl);
        photo.setContentType(contentType);
        return save(photo);
    }

    @Override
    public Photo findById(Long id) throws ResourceNotFoundException {
        return photoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 사진입니다.: " + id));
    }

    @Override
    public Photo analyzeAndSavePhotoDetails(String filePath, Photo photo) throws AIAnalysisException {
        try {
            PhotoAnalysisResponse analyzedPhoto = aiService.analyzePhoto(filePath);
            photo.setTitle(analyzedPhoto.getTitle());
            photo.setSummary(analyzedPhoto.getSummary());
            photo.setLocation(analyzedPhoto.getLocation());
            photo.setStartDate(analyzedPhoto.getStartDate());
            photo.setEndDate(analyzedPhoto.getEndDate());
            photo.setCharge(analyzedPhoto.getCharge());
            photo.setBankAccount(analyzedPhoto.getBankAccount());
            return save(photo);
        } catch (Exception e) {
            throw new AIAnalysisException();
        }
    }
}
