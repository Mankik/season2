package com.ivon.purba.web;

import com.ivon.purba.domain.User;
import com.ivon.purba.dto.smsService.SmsServiceSendRequest;
import com.ivon.purba.dto.smsService.SmsServiceSendResponse;
import com.ivon.purba.dto.smsService.SmsServiceVerifyRequest;
import com.ivon.purba.dto.smsService.SmsServiceVerifyResponse;
import com.ivon.purba.service.SmsServiceImpl;
import com.ivon.purba.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class SmsController {
    private final SmsServiceImpl smsService;
    private final UserServiceImpl userService;

    @PostMapping("sms/send")
    public ResponseEntity<SmsServiceSendResponse> sendVerificationCode(@RequestBody SmsServiceSendRequest request) {
        String phoneNumber = request.getTo();
        String verificationCode = smsService.makeVerificationCode();
        SingleMessageSentResponse singleMessageSentResponse = smsService.sendOne(phoneNumber, verificationCode);

        SmsServiceSendResponse response = new SmsServiceSendResponse(singleMessageSentResponse, verificationCode);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("sms/verified")
    public ResponseEntity<SmsServiceVerifyResponse> verify(@RequestBody SmsServiceVerifyRequest request) {
        String phoneNumber = request.getTo();
        userService.setNewVerificationCode(userService.getUserByPhoneNumber(phoneNumber));

        SmsServiceVerifyResponse response = new SmsServiceVerifyResponse();
        return ResponseEntity.ok(response);
    }
}
