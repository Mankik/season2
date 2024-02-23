package com.ivon.purba.repository;

import com.ivon.purba.domain.ContentType;
import com.ivon.purba.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentTypeRepository extends JpaRepository<ContentType, Long> {
    ContentType findByName(String typeName);
}
