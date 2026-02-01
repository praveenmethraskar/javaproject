package com.appprotech.campaign.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceAttributesInfo {

    private String deviceName;

    private String deviceModel;


    private String osVersion;


    private String appVersion;

    private String deviceId;
}
