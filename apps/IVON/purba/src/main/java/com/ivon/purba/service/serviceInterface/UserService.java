package com.ivon.purba.service.serviceInterface;

import com.ivon.purba.domain.User;

public interface UserService {
    Boolean signUp(User user);
    User signIn(String phoneNumber);
    User findById(Long id);
}
