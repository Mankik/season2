package com.ivon.purba.dto.userController;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInResponse {
    private Long userId;
    private String message;

    public SignInResponse(String message) {
        this.message = message;
    }
}
