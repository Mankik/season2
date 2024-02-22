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

    //회원가입
    public Boolean singUp(User user) {
        validateDuplicationUser(user);

        userRepository.save(user);
        return true;
    }

    //로그인
    public User signIn(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }


    private void validateDuplicationUser(User user) {
        User findUser = userRepository.findByPhoneNumber(user.getPhoneNumber());
        if (!(findUser == null)) {
            throw new UserAlreadyExistException();
        }
    }

    public User findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("해당 회원 정보가 없습니다.");
        }
        return userOptional.get();
    }
}
