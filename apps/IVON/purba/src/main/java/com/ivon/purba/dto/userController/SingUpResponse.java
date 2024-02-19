package com.ivon.purba.dto.userController;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingUpResponse {
    private String name;
    private String phoneNumber;
    private String message;

    public SingUpResponse(String message) {
        this.message = message;
    }
}
