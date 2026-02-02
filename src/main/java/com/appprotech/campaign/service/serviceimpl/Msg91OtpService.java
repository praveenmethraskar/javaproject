//package com.appprotech.campaign.service.serviceimpl;
//
//import com.appprotech.campaign.service.OtpService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import java.time.Duration;
//import java.util.Map;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class Msg91OtpService implements OtpService {
//
//    private final WebClient webClient;
//
//    @Value("${otp.msg91.auth-key}")
//    private String authKey;
//
//    @Value("${otp.msg91.sender-id}")
//    private String senderId;
//
//    @Value("${otp.msg91.template-id}")
//    private String templateId;
//
////            @Override
////            public void sendOtp(long mobile, String otp) {
////
////                Map<String, Object> body = Map.of(
////                        "template_id", templateId,
////                        "sender", senderId,
////                        "mobiles", mobile,
////                        "var1", otp
////                );
////
////                webClient.post()
////                        .uri("https://control.msg91.com/api/v5/otp")
////                        .header("authkey", authKey)
////                        .bodyValue(body)
////                        .retrieve()
////                        .bodyToMono(String.class)
////                        .doOnSuccess(response ->
////                                log.info("OTP sent successfully to {}", mobile))
////                        .doOnError(error ->
////                                log.error("Failed to send OTP to {}", mobile, error))
////                        .block();
////            }
//
//
//
//    @Override
//    public void sendOtp(long mobile, String otp) {
//
//        Map<String, Object> body = Map.of(
//                "template_id", templateId,
//                "sender", senderId,
//                "mobiles", mobile,
//                "var1", otp
//        );
//
//        webClient.post()
//                .uri("https://control.msg91.com/api/v5/otp")
//                .header("authkey", authKey)
//                .bodyValue(body)
//                .retrieve()
//                .bodyToMono(String.class)
//                .timeout(Duration.ofSeconds(5))   // ⏱ prevent hanging
//                .subscribe(
//                        response -> log.info("OTP sent successfully to {}", mobile),
//                        error -> log.error("Failed to send OTP to {}", mobile, error)
//                );
//    }
//
//}
