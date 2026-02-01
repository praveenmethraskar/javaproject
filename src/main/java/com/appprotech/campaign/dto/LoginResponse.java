package com.appprotech.campaign.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class    LoginResponse {

//
//    private Long userId;
//    private String username;
//    private String name;
    private long mobile;
//    private String role;
    private String message;
    private String deviceId;
//    private


}
