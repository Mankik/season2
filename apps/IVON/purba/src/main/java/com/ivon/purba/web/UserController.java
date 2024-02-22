package com.ivon.purba.web;

import com.ivon.purba.domain.User;
import com.ivon.purba.dto.userController.SignInRequest;
import com.ivon.purba.dto.userController.SignInResponse;
import com.ivon.purba.dto.userController.SignUpRequest;
import com.ivon.purba.dto.userController.SingUpResponse;
import com.ivon.purba.exception.UserAlreadyExistException;
import com.ivon.purba.exception.UserNotFoundException;
import com.ivon.purba.service.UserService;
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
    private final UserService userService;

    //로그인
    @GetMapping(value = "/user/signIn")
    public ResponseEntity<Object> userSignIn(@RequestBody SignInRequest request) {
        User user = userService.signIn(request.getPhoneNumber());

        SignInResponse response = new SignInResponse("로그인을 성공했습니다.");
        response.setUserId(user.getUserId());
        return ResponseEntity.ok(response);
    }

    //회원가입
    @PostMapping(value = "/user/signUp")
    public ResponseEntity<?> userSignUp(@RequestBody SignUpRequest request) {
        User member = new User();
        member.setName(request.getName());
        member.setPhoneNumber(request.getPhoneNumber());

        userService.signUp(member);
        SingUpResponse singUpResponse = new SingUpResponse("회원가입을 성공했습니다!");
        singUpResponse.setName(member.getName());
        singUpResponse.setPhoneNumber(member.getPhoneNumber());

        return ResponseEntity.status(HttpStatus.CREATED).body(singUpResponse);
    }
}
