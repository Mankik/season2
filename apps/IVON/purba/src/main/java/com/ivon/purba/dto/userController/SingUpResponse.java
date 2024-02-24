package com.ivon.purba.dto.userController;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingUpResponse {
    private String message;

    public SingUpResponse() {
        this.message = "회원가입을 성공했습니다!";
    }
}
