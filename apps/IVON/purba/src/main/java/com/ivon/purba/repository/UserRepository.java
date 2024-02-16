package com.ivon.purba.repository;

import com.ivon.purba.domain.User;
import jakarta.persistence.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@Repository
public class UserRepository {
    @PersistenceContext
    EntityManager em;

    public Long save(User user) {
        em.persist(user);
        return user.getId();
    }

    public User findByPhoneNumber(String phoneNumber) {
        try{
            return em.createQuery("select u from User u where u.phoneNumber = :phoneNumber", User.class)
                    .setParameter("phoneNumber", phoneNumber)
                    .getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }
}
