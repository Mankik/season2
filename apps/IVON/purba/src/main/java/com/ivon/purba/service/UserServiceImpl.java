package com.ivon.purba.service;

import com.ivon.purba.domain.User;
import com.ivon.purba.dto.userController.SignUpRequest;
import com.ivon.purba.exception.ResourceNotFoundException;
import com.ivon.purba.exception.UserAlreadyExistException;
import com.ivon.purba.exception.UserNotFoundException;
import com.ivon.purba.repository.UserRepository;
import com.ivon.purba.service.serviceInterface.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Value("${app.validVerificationCodeHours}")
    private static int validVerificationCodeHours;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원가입
    @Override
    public Boolean signUp(User user) {
        validateDuplicationUser(user);
        userRepository.save(user);

        return true;
    }

    // 로그인
    @Override
    public Long signIn(String phoneNumber) {
        User user = getUserByPhoneNumber(phoneNumber);
        LocalDateTime now = LocalDateTime.now();
        String verificationCode = user.getVerificationCode(); //과거의 인증 코드

        if (verificationCode == null || !verificationCode.equals(makeVerificationCode(user)) || !checkCredTimeFromNow(user, now)) {
            throw new ResourceNotFoundException("전화번호 인증이 필요합니다.");
        }

        return user.getId();
    }

    @Override
    public User getUserByPhoneNumber(String phoneNumber) {
        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);
        return userOptional.orElseThrow(() -> new UserNotFoundException("해당 회원이 존재하지 않습니다."));
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("해당 ID로 회원를 조회할 수 없습니다."));
    }

    @NotNull
    public User createUser(SignUpRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setPhoneNumber(request.getPhoneNumber());

        user.setVerificationCode(setNewVerificationCode(user));
        return user;
    }

    private void validateDuplicationUser(User user) {
        userRepository.findByPhoneNumber(user.getPhoneNumber())
                .ifPresent(u -> {
                    throw new UserAlreadyExistException("이미 존재하는 유저입니다.");
                });
    }

    public String makeVerificationCode(User user) {
        return hashPhoneNumber(user.getPhoneNumber());
    }

    private String hashPhoneNumber(String phoneNumber) {
        return Integer.toString(phoneNumber.hashCode());
    }

    public String setNewVerificationCode(User user) {
        //여기에 세션에 대한 정보를 넣을 것임
        LocalDateTime now = LocalDateTime.now();
        String newCode = hashPhoneNumber(user.getPhoneNumber());
        try {
            user.setVerificationCode(newCode);
            user.setCodeCreTime(now);
        } catch (DataIntegrityViolationException e) { //중복 코드 방지
            newCode = setNewVerificationCode(user);
        }
        return newCode;
    }

    private static boolean checkCredTimeFromNow(User user, LocalDateTime now) {
        if (user.getCodeCreTime() != null) {
            long hoursElapsed = ChronoUnit.HOURS.between(user.getCodeCreTime(), now);
            if (hoursElapsed <= validVerificationCodeHours) {
                return true;
            }
        }
        return false;
    }
}
