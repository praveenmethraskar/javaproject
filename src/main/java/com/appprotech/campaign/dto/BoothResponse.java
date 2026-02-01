package com.appprotech.campaign.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoothResponse {


    private Long id;

    private long boothNumber;

    private String assemblyConstituency;

    private String parliamentary;

    private long userId;


}
