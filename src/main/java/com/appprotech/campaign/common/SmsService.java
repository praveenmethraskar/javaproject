package com.appprotech.campaign.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.MessageAttributeValue;
import software.amazon.awssdk.services.sns.model.PublishRequest;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmsService {

    private final SnsClient snsClient;

    @Value("${aws.sns.sender-id}")
    private String senderId;


    @Value("${aws.region}")
    private String region;



    public void sendOtp(long mobile, String otp) {
        try {
            String message = "Your OTP is " + otp + ". Valid for 5 minutes.";

            PublishRequest request = PublishRequest.builder()
                    .phoneNumber("+91" + mobile)
                    .message(message)
                    .messageAttributes(Map.of(
                            "AWS.SNS.SMS.SenderID",
                            MessageAttributeValue.builder()
                                    .stringValue(senderId)
                                    .dataType("String")
                                    .build(),
                            "AWS.SNS.SMS.SMSType",
                            MessageAttributeValue.builder()
                                    .stringValue("Transactional")
                                    .dataType("String")
                                    .build()
                    ))
                    .build();

            snsClient.publish(request);

            log.info("OTP sent successfully to {}", mobile);

        } catch (Exception e) {
            log.error("Failed to send OTP to {}", mobile, e);
            throw new RuntimeException("OTP delivery failed");
        }
    }

}
