package com.ivon.purba.service.serviceInterface;

import com.ivon.purba.domain.User;

public interface UserService {
    Boolean signUp(User user);
    Long signIn(String phoneNumber);

    User getUserByPhoneNumber(String phoneNumber);

    User getUserById(Long id);
}
