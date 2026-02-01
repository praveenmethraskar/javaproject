package com.appprotech.campaign.service;

import com.appprotech.campaign.common.APIResponse;
import com.appprotech.campaign.dto.*;
import com.appprotech.campaign.entity.Voter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VoterService {

    public List<Voter> insertData(VoterBulkRequest originalDataRequest);

    List<VoterResponse> getAllVoters(HttpServletRequest request);

    public VoterResponse getVoterByEpicId(String epicId, HttpServletRequest request);

    ResponseEntity<APIResponse<VoterUpdateResponse>> updateVoter(VoterUpdateRequest voterUpdateRequest, HttpServletRequest request);
}
