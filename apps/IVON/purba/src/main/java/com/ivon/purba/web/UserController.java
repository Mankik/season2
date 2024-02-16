package com.ivon.purba.web;

import com.ivon.purba.domain.User;
import com.ivon.purba.dto.CreateUserRequest;
import com.ivon.purba.dto.UserResponse;
import com.ivon.purba.service.UserService;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(value = "/users/new")
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest request) {
        User member = new User();
        member.setName(request.getName());
        member.setPhoneNumber(request.getPhoneNumber());
        userService.join(member);

        UserResponse userResponse = new UserResponse();
        userResponse.setName(member.getName());
        userResponse.setPhoneNumber(member.getPhoneNumber());

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }
}
