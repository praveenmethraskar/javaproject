package com.appprotech.campaign.dto;

import lombok.Data;

@Data
public class OtpVerificationequest {


    private Long otp;

    private Long phoneNumber;

    private DeviceAttributesInfo deviceAttrributes;

}
