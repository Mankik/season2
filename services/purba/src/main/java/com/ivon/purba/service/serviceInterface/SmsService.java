package com.ivon.purba.service.serviceInterface;

import jakarta.annotation.PostConstruct;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;

public interface SmsService {

    SingleMessageSentResponse sendOne(String to, String verificationCode);

    String makeVerificationCode();
}
