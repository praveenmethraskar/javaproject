package com.appprotech.campaign.controller;

import com.appprotech.campaign.common.APIResponse;
import com.appprotech.campaign.dto.BoothResponse;
import com.appprotech.campaign.dto.DeviceAttributesInfo;
import com.appprotech.campaign.service.BoothService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/voterbooths")
@RequiredArgsConstructor
public class BoothsController {


    @Autowired
    private BoothService boothService;

    @GetMapping
    public ResponseEntity<APIResponse<List<BoothResponse>>> getAllUsers( HttpServletRequest  request) {
        return ResponseEntity.ok(APIResponse.<List<BoothResponse>>builder()
                .success(true)
                .statusCode(200)
                .message("User booths fetched successfully")
                .data(boothService.getAllBooths(request))
                .build());
    }

}
