package com.appprotech.campaign.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonVoterData {

    private String villageOrMaintown;

    private String mandal;

    private String revenueDivision;

    private String district;

    private String state;

    private String policeStation;

    private String postOffice;

    private String assemblyConstituency;

    private String parliamentary;

    private long boothNumber;

    private String pollingStation;

    private String NoAndNameOfSectionsInThePart;

    private String addressOfPollingStation;

    private String pincode;
}
