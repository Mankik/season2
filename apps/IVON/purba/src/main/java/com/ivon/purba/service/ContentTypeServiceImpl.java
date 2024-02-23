package com.ivon.purba.service;

import com.ivon.purba.domain.ContentType;
import com.ivon.purba.exception.ResourceNotFoundException;
import com.ivon.purba.repository.ContentTypeRepository;
import com.ivon.purba.service.serviceInterface.ContentTypeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ContentTypeServiceImpl implements ContentTypeService {

    private final ContentTypeRepository contentTypeRepository;

    @Autowired
    public ContentTypeServiceImpl(ContentTypeRepository contentTypeRepository) {
        this.contentTypeRepository = contentTypeRepository;
    }

    public ContentType getContentType(String typeName) {
        try {
            return contentTypeRepository.findByName(typeName);
        } catch (Exception e) {
            throw new ResourceNotFoundException("존재하지 않는 콘텐트 유형입니다.:" + e.getMessage());
        }
    }

}
