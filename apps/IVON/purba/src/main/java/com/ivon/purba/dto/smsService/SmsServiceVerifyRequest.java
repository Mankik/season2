package com.ivon.purba.dto.smsService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsServiceVerifyRequest {
    private String to;
    private String code;
}
