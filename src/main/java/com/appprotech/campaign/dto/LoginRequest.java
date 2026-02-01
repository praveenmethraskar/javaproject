package com.appprotech.campaign.dto;


import lombok.Data;

@Data
public class LoginRequest {

    private Long phoneNumber;

    private DeviceAttributesInfo deviceAttrributes;
}
