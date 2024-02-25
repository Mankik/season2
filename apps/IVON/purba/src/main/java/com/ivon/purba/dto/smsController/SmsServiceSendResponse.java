package com.ivon.purba.dto.smsController;

import lombok.Getter;
import lombok.Setter;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;

@Getter
@Setter
public class SmsServiceSendResponse {
    private SingleMessageSentResponse singleMessageSentResponse;
    private String verificationCode;

    public SmsServiceSendResponse(SingleMessageSentResponse singleMessageSentResponse, String verificationCode) {
        this.singleMessageSentResponse = singleMessageSentResponse;
        this.verificationCode = verificationCode;
    }
}
