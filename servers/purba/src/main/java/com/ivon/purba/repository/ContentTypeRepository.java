package com.ivon.purba.repository;

import com.ivon.purba.domain.ContentType;
import com.ivon.purba.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContentTypeRepository extends JpaRepository<ContentType, Long> {
    Optional<ContentType> findByName(String typeName);
}
