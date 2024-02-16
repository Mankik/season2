package com.ivon.purba.service;

import com.ivon.purba.domain.User;
import com.ivon.purba.repository.UserRepository;
import jakarta.persistence.NoResultException;
import jakarta.validation.constraints.Null;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional // только для чтения и записи только для одного запроса к БД и т.д
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
        if (user == null){
            throw new IllegalStateException("해당 회원 정보가 없습니다.");
        }
        return user;
    }


    private void validateDuplicationUser(User user) {
        User findUser = userRepository.findByPhoneNumber(user.getPhoneNumber());
        if (!(findUser == null)) {
            throw new IllegalStateException("이미 존재하는 회원입니다."); // 예외 발생시키기 위해 사용한다. 예외 처리 코드를 작성하는 것이 좋다.
        }
    }
}
