package com.appprotech.campaign.controller;

import com.appprotech.campaign.common.APIResponse;
import com.appprotech.campaign.dto.*;
import com.appprotech.campaign.entity.Voter;
import com.appprotech.campaign.service.VoterService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/voter")
public class VoterController {

    @Autowired
    private VoterService voterService;

//    @PostMapping("/insertrecords")
//    public ResponseEntity<APIResponse<Map<String, Object>>> saveData(
//            @RequestBody VoterBulkRequest request) {
//        List<Voter> saved = voterService.insertData(request);
//        Map<String, Object> response = new HashMap<>();
//        response.put("total", request.getRecords().size());
//        response.put("inserted", saved.size());
//        response.put("skipped", request.getRecords().size() - saved.size());
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(APIResponse.success(
//                        201,
//                        "Voter records processed successfully",
//                        response
//                ));
//    }


    @GetMapping("/voters")
    public ResponseEntity<APIResponse<List<VoterResponse>>> getAllVoters(HttpServletRequest request) {
        List<VoterResponse> voters = voterService.getAllVoters(request);
        return ResponseEntity.ok(
                APIResponse.success(
                        200,
                        "Voters fetched successfully",
                        voters
                )
        );
    }

    @GetMapping("/voters/{epicId}")
    public ResponseEntity<APIResponse<VoterResponse>> getVoterByEpicId(
            @PathVariable String epicId,HttpServletRequest request) {
        VoterResponse voter = voterService.getVoterByEpicId(epicId,request);
        return ResponseEntity.ok(
                APIResponse.success(
                        200,
                        "Voter fetched successfully",
                        voter
                )
        );
    }


    @PutMapping("/updatevoter")
    public ResponseEntity<APIResponse<VoterUpdateResponse>> updateVoter(
            @RequestBody VoterUpdateRequest voterUpdateRequest, HttpServletRequest request) {
        return voterService.updateVoter(voterUpdateRequest,request);
    }










}
