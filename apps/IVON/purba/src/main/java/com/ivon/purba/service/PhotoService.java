package com.ivon.purba.service;

import com.ivon.purba.domain.Photo;
import com.ivon.purba.exception.PhotoSaveException;
import com.ivon.purba.exception.ResourceNotFoundException;
import com.ivon.purba.repository.PhotoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PhotoService {

    private final PhotoRepository photoRepository;

    @Autowired
    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public Photo save(Photo photo) {
        try {
            return photoRepository.save(photo);
        } catch (DataAccessException e) {
            throw new PhotoSaveException("사진 저장 실패: " + e.getMessage());
        }
    }

    public List<Photo> findAllPhotoByUserId(Long userId) {
        try {
            return photoRepository.findByUser_UserId(userId);
        } catch (DataAccessException e) {
            throw new ResourceNotFoundException("사진 불러오기 실패: " + e.getMessage());
        }
    }
}
