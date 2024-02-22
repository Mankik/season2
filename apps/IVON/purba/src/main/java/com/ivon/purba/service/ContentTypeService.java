package com.ivon.purba.service;

import com.ivon.purba.domain.Content;
import com.ivon.purba.domain.ContentType;
import com.ivon.purba.domain.Photo;
import com.ivon.purba.domain.User;
import com.ivon.purba.exception.AIAnalysisException;
import com.ivon.purba.exception.PhotoSaveException;
import com.ivon.purba.exception.ResourceNotFoundException;
import com.ivon.purba.exception.UserAlreadyExistException;
import com.ivon.purba.repository.ContentTypeRepository;
import com.ivon.purba.repository.PhotoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ContentTypeService {

    private final ContentTypeRepository contentTypeRepository;

    @Autowired
    public ContentTypeService(ContentTypeRepository contentTypeRepository) {
        this.contentTypeRepository = contentTypeRepository;
    }

    public ContentType getContentType(String typeName) {
        try {
            return contentTypeRepository.findByTypeName(typeName);
        } catch (Exception e) {
            throw new ResourceNotFoundException("존재하지 않는 콘텐트 유형입니다.:" + e.getMessage());
        }
    }

}
