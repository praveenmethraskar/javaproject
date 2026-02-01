package com.appprotech.campaign.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoterResponse {


    private Long id;

    private String epicId;

    private String name;

    private String relativeName;

    private String relativeRelation;

    private String houseNumber;

    private String Gender;

    private String villageOrMaintown;

    private String assemblyConstituency;

    private String parliamentary;

    private long boothNumber;

    private String addressOfPollingStation;

    private String mandal;

    private String revenueDivision;

    private String district;

    private String state;

    private String pollingStation;






}
