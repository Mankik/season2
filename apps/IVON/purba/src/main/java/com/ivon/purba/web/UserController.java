package com.ivon.purba.web;

import com.ivon.purba.domain.User;
import com.ivon.purba.repository.UserRepository;
import com.ivon.purba.service.UserService;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(value = "/users/new")
    public String createUser(@RequestBody CreateUserRequest request) {
        User member = new User();
        member.setName(request.getName());
        member.setPhoneNumber(request.getPhoneNumber());
        userService.join(member);
        return "user/new";
    }
}
