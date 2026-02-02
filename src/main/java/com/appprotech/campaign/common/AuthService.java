package com.appprotech.campaign.common;

import com.appprotech.campaign.dto.LoginRequest;
import com.appprotech.campaign.dto.LoginResponse;
import com.appprotech.campaign.dto.OtpVerificationResponse;
import com.appprotech.campaign.dto.OtpVerificationequest;
import com.appprotech.campaign.entity.LoginDetails;
import com.appprotech.campaign.entity.OtpVerification;
import com.appprotech.campaign.entity.User;
import com.appprotech.campaign.enums.LoginFailureReason;
import com.appprotech.campaign.enums.LoginStatus;
import com.appprotech.campaign.repository.LoginDetailsRepository;
import com.appprotech.campaign.repository.OtpRepository;
import com.appprotech.campaign.repository.UserRepository;
import com.appprotech.campaign.service.BoothService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final OtpRepository otpRepository;

    @Autowired
    private  PasswordEncoder passwordEncoder;
    private final OtpUtil otpUtil;
    private final SmsService smsService;
    private final JwtUtil jwtUtil;

    private final BoothService boothService;

    @Autowired
    private  DeviceAttributesInfoMapper deviceAttributesInfoMapper;
    private final LoginDetailsRepository loginDetailsRepository;

    // STEP 1: Login & Send OTP
        public LoginResponse login(LoginRequest loginRequest) {
            if (loginRequest.getPhoneNumber() == null) {
                throw new IllegalArgumentException("Phone number must not be null");
            }
            LoginDetails loginDetails = new LoginDetails();
            loginDetails.setLoginAt(LocalDateTime.now());
            loginDetails.setPhoneNumber(loginRequest.getPhoneNumber());
            loginDetails.setDeviceAttributes(
                    deviceAttributesInfoMapper.mapTEntity(
                            loginRequest.getDeviceAttrributes()
                    )
            );

            User user = userRepository.findByPhoneNumber(loginRequest.getPhoneNumber())
                    .orElseThrow(() -> {
                        loginDetails.setStatus(LoginStatus.FAILED);
                        loginDetails.setFailureReason(LoginFailureReason.USER_NOT_FOUND);
                        loginDetails.setSuspicious(true);
                        loginDetails.setUser(null);
                         loginDetailsRepository.save(loginDetails); // IMPORTANT
                        return new RuntimeException(
                                "User not found with phone number: " +
                                        loginRequest.getPhoneNumber()
                        );
                    });
            loginDetails.setStatus(LoginStatus.pending);
            loginDetails.setFailureReason(null);
            loginDetails.setSuspicious(false);
            loginDetails.setUser(user);
             loginDetailsRepository.save(loginDetails); // IMPORTANT
            try {
                otpUtil.generateAndSendOtp(user, loginRequest);
            } catch (Exception ex) {
                loginDetails.setStatus(LoginStatus.FAILED);
                loginDetails.setFailureReason(LoginFailureReason.OTP_SERVICE_TIMEOUT);
                loginDetails.setSuspicious(true);
                loginDetailsRepository.save(loginDetails);
                throw new RuntimeException("OTP service is temporarily unavailable. Please try again.");
            }
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setMessage("OTP Sent Successfully");
            loginResponse.setDeviceId(loginRequest.getDeviceAttrributes().getDeviceId());
            loginResponse.setMobile(user.getPhoneNumber());
            return loginResponse;






//            User user = userRepository.findByPhoneNumber(loginRequest.getPhoneNumber());
//            LoginDetails loginDetails=new LoginDetails();
//            loginDetails.setLoginAt(LocalDateTime.now());
//            loginDetails.setPhoneNumber(loginRequest.getPhoneNumber());
//            loginDetails.setDeviceAttributes(deviceAttributesInfoMapper.mapTEntity(loginRequest.getDeviceAttrributes()));
//            if (user== null) {
//                loginDetails.setStatus(LoginStatus.FAILED);
//                loginDetails.setFailureReason(LoginFailureReason.USER_NOT_FOUND);
//                loginDetails.setSuspicious(true);
//                loginDetails.setUser(null);
//                throw new RuntimeException("User not found with phone number: "
//                        + loginRequest.getPhoneNumber());
//            }
//            else{
//                loginDetails.setStatus(LoginStatus.pending);
//                loginDetails.setFailureReason(null);
//                loginDetails.setSuspicious(false);
//                loginDetails.setUser(user);
//                otpUtil.generateAndSendOtp(user,loginRequest);
//                String response="OTP Sent Successfully";
//                LoginResponse loginResponse=new LoginResponse();
//                loginResponse.setMessage(response);
//                loginResponse.setDeviceId(loginRequest.getDeviceAttrributes().getDeviceId());
//                loginResponse.setMobile(user.getPhoneNumber());
//                return loginResponse;
//            }
        }



    @Transactional
    public OtpVerificationResponse verifyOtp(OtpVerificationequest otpVerificationequest) {
       Optional<User>  user = userRepository.findByPhoneNumber(otpVerificationequest.getPhoneNumber());
//                .orElseThrow(() -> new RuntimeException("Invalid userId"));

        OtpVerification entity = otpRepository
                .findTopByMobileAndVerifiedFalseOrderByIdDesc(user.get().getPhoneNumber())
                .orElseThrow(() -> new RuntimeException("OTP not found"));
        // Expiry check
//        if (entity.getExpiresAt().isBefore(LocalDateTime.now())) {
//            throw new RuntimeException("OTP expired");
//        }
//        // Attempt limit
//        if (entity.getAttempts() >= 3) {
//            throw new RuntimeException("OTP attempts exceeded");
//        }
//        // OTP mismatch
//        if (entity.getOtp().equals(otpVerificationequest.getOtp())) {
//            entity.setAttempts(entity.getAttempts() + 1);
//            otpRepository.save(entity);
//            throw new RuntimeException("Invalid OTP");
//        }
        // OTP success
        entity.setVerified(true);
        entity.setAttempts(entity.getAttempts() + 1);
        otpRepository.save(entity);
        // Fetch user by mobile

        userRepository.save(user.get());
        String token =jwtUtil.generateToken(user.get());
        user.get().setActiveToken(token);
      User userResponse=  userRepository.save(user.get());
        OtpVerificationResponse res=new OtpVerificationResponse();
        res.setId(userResponse.getId());
        res.setEmail(userResponse.getEmail());
        res.setFirstName(userResponse.getFirstName());
        res.setLastName(userResponse.getLastName());
        res.setUsername(userResponse.getUsername());
        res.setPhoneNumber(userResponse.getPhoneNumber());
        res.setReferredPerson(userResponse.getReferredPerson());
        res.setToken(token);
        res.setBoothResponse(boothService.getAllBoothsByUserId(user.get().getId()));
        return res;
    }


}
