package com.ivon.purba.web;

import com.ivon.purba.domain.User;
import com.ivon.purba.repository.UserRepository;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserRepository personRepository;
    @PostMapping("/users/save")
    public void userSave(@RequestBody User user) {
        personRepository.save(user);
    }
}
