package com.appprotech.campaign.common;

import org.springframework.stereotype.Service;

@Service
public class SmsService {

    public void sendOtp(long mobile, String otp) {
        // Integrate Twilio / AWS SNS / MSG91
        System.out.println("OTP sent to " + mobile + ": " + otp);
    }
}
