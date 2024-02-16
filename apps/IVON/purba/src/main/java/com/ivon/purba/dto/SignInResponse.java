package com.ivon.purba.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInResponse {
    private Long user_id;
    private String message;

    public SignInResponse(String message) {
        this.message = message;
    }

    public void setUserId(Long id) {
        this.user_id = id;
    }
}
