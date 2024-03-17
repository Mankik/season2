package com.ivon.purba.service;

import com.ivon.purba.service.serviceInterface.SmsService;
import jakarta.annotation.PostConstruct;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;

@Component
public class SmsServiceImpl implements SmsService {

    @Value("${coolsms.api.key}")
    private String apiKey;

    @Value("${coolsms.api.secret}")
    private String apiSecretKey;

    @Value("${app.myPhoneNumber}")
    private static String myPhoneNumber;

    private DefaultMessageService messageService;

    @PostConstruct
    protected void init() {
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, "https://api.coolsms.co.kr");
    }

    @Override
    public SingleMessageSentResponse sendOne(String to, String verificationCode) {
        Message message = setMessageForm(to, verificationCode);

        return this.messageService.sendOne(new SingleMessageSendingRequest(message));
    }

    @Override
    public String makeVerificationCode() {
        return String.valueOf((int) (Math.random() * 8999) + 1000);
    }

    private static Message setMessageForm(String to, String verificationCode) {
        Message message = new Message();
        message.setFrom(myPhoneNumber);
        message.setTo(to);
        message.setText("문자 인증 번호입니다.\n" + verificationCode);
        return message;
    }
}
