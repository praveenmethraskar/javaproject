package com.appprotech.campaign.common;

import com.appprotech.campaign.dto.LoginRequest;
import com.appprotech.campaign.entity.OtpVerification;
import com.appprotech.campaign.entity.User;
import com.appprotech.campaign.repository.OtpRepository;
import com.appprotech.campaign.service.OtpService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
public class OtpUtil {


    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private OtpService otpService;

    @Autowired
    private Msg91OtpService msg91OtpService;

    public String generateOtp() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }

    @Transactional
    public void generateAndSendOtp(User user, LoginRequest loginRequests) {
        String otp = generateOtp();
//        List<OtpVerification> previousOtps =
//                otpRepository.findAllPreviousOtps(
//                        loginRequests.getPhoneNumber(),
//                        user.getId()
//                );

        List<OtpVerification> previousOtps =
                otpRepository.findAllPreviousOtps(
                        loginRequests.getPhoneNumber(),
                        user.getId()
                );

        if (!previousOtps.isEmpty()) {
            previousOtps.forEach(otpp -> otpp.setVerified(true));
            otpRepository.saveAll(previousOtps);
        }


        OtpVerification entity = OtpVerification.builder()
                .mobile(user.getPhoneNumber())
                .userId(user.getId())
                .deviceId(loginRequests.getDeviceAttrributes().getDeviceId())
                .otp(otp)
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .verified(false)
                .attempts(0)
                .createdAt(LocalDateTime.now())
                .build();
        otpRepository.save(entity);
//        otpService.sendOtp(user.getPhoneNumber(), otp);
        msg91OtpService.sendLoginOtp(user.getPhoneNumber(),otp);
    }


}
