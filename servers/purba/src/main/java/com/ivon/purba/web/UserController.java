package com.ivon.purba.web;

import com.ivon.purba.domain.User;
import com.ivon.purba.dto.userController.SignInRequest;
import com.ivon.purba.dto.userController.SignInResponse;
import com.ivon.purba.dto.userController.SignUpRequest;
import com.ivon.purba.dto.userController.SingUpResponse;
import com.ivon.purba.exception.ResourceNotFoundException;
import com.ivon.purba.service.SmsServiceImpl;
import com.ivon.purba.service.UserServiceImpl;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    //로그인
    @GetMapping(value = "/user/signIn")
    public ResponseEntity<Object> userSignIn(@RequestBody SignInRequest request) {
        Long userId = userService.signIn(request.getPhoneNumber());

        SignInResponse response = new SignInResponse(userId);
        return ResponseEntity.ok(response);
    }

    //회원가입
    @PostMapping(value = "/user/signUp")
    public ResponseEntity<?> userSignUp(@RequestBody SignUpRequest request) {
        userService.signUp(userService.createUser(request));

        SingUpResponse singUpResponse = new SingUpResponse();
        return ResponseEntity.status(HttpStatus.CREATED).body(singUpResponse);
    }
}
