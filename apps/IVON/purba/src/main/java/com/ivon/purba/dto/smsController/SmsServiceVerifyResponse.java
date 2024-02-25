package com.ivon.purba.dto.smsController;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsServiceVerifyResponse {
    private String message;

    public SmsServiceVerifyResponse() {
        this.message = "요청이 확인되었습니다.";
    }
}
