package com.appprotech.campaign.common;

import com.appprotech.campaign.dto.LoginRequest;
import com.appprotech.campaign.dto.LoginResponse;
import com.appprotech.campaign.dto.OtpVerificationResponse;
import com.appprotech.campaign.dto.OtpVerificationequest;
import com.appprotech.campaign.service.BoothService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<APIResponse<LoginResponse>> login(
            @RequestBody LoginRequest loginRequest
           ) {

        LoginResponse response= authService.login(loginRequest);
        return ResponseEntity.ok(
                APIResponse.success(200, "OTP sent to mobile", response)
        );
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<APIResponse<OtpVerificationResponse>> verifyOtp(
            @RequestBody OtpVerificationequest otpVerificationequest) {
        OtpVerificationResponse otpVerificationResponse = authService.verifyOtp(otpVerificationequest);
        return ResponseEntity.ok(
                APIResponse.success(200, "Login successful", otpVerificationResponse)
        );
    }
}
