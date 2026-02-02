package com.appprotech.campaign.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
public class Msg91OtpService {

    @Value("${msg91.host}")
    private String host;

    @Value("${msg91.path}")
    private String path;

    @Value("${msg91.auth-key}")
    private String authKey;

    @Value("${msg91.login-campaign}")
    private String flowId;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendLoginOtp(long mobile, String otp) {

        String url = "https://" + host + path;

        Map<String, Object> recipient = Map.of(
                "mobiles", "91" + mobile,
                "OTP", otp
        );

        Map<String, Object> payload = Map.of(
                "flow_id", flowId,
                "recipients", List.of(recipient)
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authkey", authKey);

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<String> response =
                    restTemplate.postForEntity(url, request, String.class);

            log.info("MSG91 Login OTP sent to {}, response={}",
                    mobile, response.getBody());

        } catch (Exception e) {
            log.error("Failed to send Login OTP to {}", mobile, e);
            throw new RuntimeException("OTP SMS delivery failed");
        }
    }
}
