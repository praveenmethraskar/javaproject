package com.appprotech.campaign.common;


import com.appprotech.campaign.entity.DeviceAttributes;
import com.appprotech.campaign.dto.DeviceAttributesInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Service
public class DeviceAttributesInfoMapper {


    public DeviceAttributes mapTEntity(DeviceAttributesInfo deviceAttributesInfo){
        DeviceAttributes deviceAttributes=new DeviceAttributes();
        deviceAttributes.setDeviceMocdel(deviceAttributesInfo.getDeviceModel());
        deviceAttributes.setAppVersion(deviceAttributesInfo.getAppVersion());
        deviceAttributes.setOsVersion(deviceAttributesInfo.getOsVersion());
        deviceAttributes.setDevcieName(deviceAttributesInfo.getDeviceName() );
        deviceAttributes.setDeviceId(deviceAttributesInfo.getDeviceId());
        return deviceAttributes;
    }
}
