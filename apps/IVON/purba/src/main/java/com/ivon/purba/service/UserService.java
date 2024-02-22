package com.ivon.purba.service;

import com.ivon.purba.domain.User;
import com.ivon.purba.exception.ResourceNotFoundException;
import com.ivon.purba.exception.UserAlreadyExistException;
import com.ivon.purba.exception.UserNotFoundException;
import com.ivon.purba.repository.UserRepository;
import jakarta.persistence.NoResultException;
import jakarta.validation.constraints.Null;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원가입
    public Boolean signUp(User user) {
        validateDuplicationUser(user);
        userRepository.save(user);
        return true;
    }

    // 로그인
    public User signIn(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UserNotFoundException("해당 전화번호로 등록된 유저가 없습니다."));
    }

    private void validateDuplicationUser(User user) {
        userRepository.findByPhoneNumber(user.getPhoneNumber())
                .ifPresent(u -> {
                    throw new UserAlreadyExistException("이미 존재하는 유저입니다.");
                });
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("해당 ID로 유저를 조회할 수 없습니다."));
    }
}
