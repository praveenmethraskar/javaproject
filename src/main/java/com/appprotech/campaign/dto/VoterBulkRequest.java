package com.appprotech.campaign.dto;

import lombok.Data;

import java.util.List;

@Data
public class VoterBulkRequest {
    private CommonVoterData common;
    private List<OriginalDataRequest> records;
}
