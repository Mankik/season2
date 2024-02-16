package com.ivon.purba.service;

import com.ivon.purba.domain.User;
import com.ivon.purba.repository.UserRepository;
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

    public Long join(User user) {
        validateDuplicationUser(user);
        userRepository.save(user);
        return user.getId();
    }

    private void validateDuplicationUser(User user) {
        List<User> findUsers = userRepository.findByName(user.getName());
        if (!findUsers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다."); // 예외 발생시키기 위해 사용한다. 예외 처리 코드를 작성하는 것이 좋다.
        }
    }

    public List<User> findMembers() {
        return userRepository.findAll();
    }

    public User findOne(Long memberId) {
        return userRepository.findOne(memberId);
    }


}
