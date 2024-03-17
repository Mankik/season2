package com.ivon.purba.dto.smsController;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsServiceVerifyRequest {
    private String to;
    private String code;
}
