package com.appprotech.campaign.service.serviceimpl;

import com.appprotech.campaign.common.JwtUtil;
import com.appprotech.campaign.dto.BoothResponse;
import com.appprotech.campaign.dto.VoterResponse;
import com.appprotech.campaign.entity.BoothNumbers;
import com.appprotech.campaign.entity.User;
import com.appprotech.campaign.entity.UserBoothAccess;
import com.appprotech.campaign.entity.Voter;
import com.appprotech.campaign.repository.BoothNumbersRepository;
import com.appprotech.campaign.repository.UserRepository;
import com.appprotech.campaign.service.BoothService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class BoohServiceImpl implements BoothService {


    @Autowired
    private BoothNumbersRepository BoothNumbersRepository;


    @Autowired
    private JwtUtil jwtUtil;


    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @Override
    public List<BoothResponse> getAllBooths(HttpServletRequest request) {
        String token = jwtUtil.getToken(request);
        if (token == null) {
            throw new AccessDeniedException("Unauthorized: Token missing");
        }
        long userId = jwtUtil.getUserId(token);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<UserBoothAccess> assignedBooths = userService.getAssignedBooths(userId);
        if (assignedBooths == null || assignedBooths.isEmpty()) {
            throw new RuntimeException("User does not have any booth access");
        }
        return assignedBooths.stream()
                .map(this::mapToBoothResponse)
                .collect(Collectors.toList());
    }




    private BoothResponse mapToBoothResponse(UserBoothAccess boothNumbers){
        BoothResponse  boothResponse=new BoothResponse();
            boothResponse.setId(boothNumbers.getId());
            boothResponse.setUserId(boothNumbers.getUser().getId());
            boothResponse.setBoothNumber(boothNumbers.getBoothNumber());
            boothResponse.setParliamentary(boothNumbers.getParliamentary());
            boothResponse.setAssemblyConstituency(boothNumbers.getAssemblyConstituency());
            return boothResponse;
        }



    public List<BoothResponse> getAllBoothsByUserId(Long userId){
        List<UserBoothAccess> assignedBooths = userService.getAssignedBooths(userId);
        if (assignedBooths == null || assignedBooths.isEmpty()) {
            throw new RuntimeException("User does not have any booth access");
        }
        return assignedBooths.stream()
                .map(this::mapToBoothResponse)
                .collect(Collectors.toList());
    }




}
