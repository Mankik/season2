package com.ivon.purba.web;

import com.ivon.purba.domain.User;
import com.ivon.purba.dto.SignInRequest;
import com.ivon.purba.dto.SignInResponse;
import com.ivon.purba.dto.SignUpRequest;
import com.ivon.purba.dto.SingUpResponse;
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

    @GetMapping(value = "/user/signIn")
    public ResponseEntity<Object> userSignIn(@RequestBody SignInRequest request) {
        User user = userService.signIn(request.getPhoneNumber());
        if (!(user == null)) {
            SignInResponse response = new SignInResponse("로그인을 성공했습니다.");
            response.setUserId(user.getId());
            return ResponseEntity.ok(response); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // 401 Unauthorized
        }
    }

    @PostMapping(value = "/user/signUp")
    public ResponseEntity<SingUpResponse> userSignUp(@RequestBody SignUpRequest request) {
        User member = new User();
        member.setName(request.getName());
        member.setPhoneNumber(request.getPhoneNumber());

        // 회원가입 메서드 호출
        boolean success = userService.singUp(member);

        if (success) {
            // 회원가입 성공 시
            SingUpResponse singUpResponse = new SingUpResponse("회원가입을 성공했습니다!");
            singUpResponse.setName(member.getName());
            singUpResponse.setPhoneNumber(member.getPhoneNumber());

            return ResponseEntity.status(HttpStatus.CREATED).body(singUpResponse);
        } else {
            // 회원가입 실패 시
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
