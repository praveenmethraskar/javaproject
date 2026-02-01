package com.appprotech.campaign.dto;

import lombok.Data;

import java.util.List;

@Data
public class OtpVerificationResponse {

    String token;

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private Long phoneNumber;

    private String email;

    private String referredPerson;

    private List<BoothResponse> boothResponse;



}
