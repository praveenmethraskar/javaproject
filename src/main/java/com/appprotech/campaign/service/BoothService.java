package com.appprotech.campaign.service;

import com.appprotech.campaign.dto.BoothResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface BoothService {


    public List<BoothResponse> getAllBooths(HttpServletRequest request);

    public List<BoothResponse> getAllBoothsByUserId(Long useId);
}
