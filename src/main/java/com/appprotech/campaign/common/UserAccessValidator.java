package com.appprotech.campaign.common;

import com.appprotech.campaign.entity.BoothNumbers;
import com.appprotech.campaign.entity.User;
import com.appprotech.campaign.entity.UserBoothAccess;
import com.appprotech.campaign.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserAccessValidator {

    @Autowired
    private UserRepository userRepository;

    /**
     * Validate access for MULTIPLE booths
     */
    public List<Long> validateBoothAccess(List<Long> requestedBooths) throws AccessDeniedException {
                long userId = 1L; // TEMP → replace with logged-in user ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<UserBoothAccess> assignedBooths = user.getAssignedBoothNumbers();

        if (assignedBooths == null || assignedBooths.isEmpty()) {
            throw new AccessDeniedException("User has no booth access");
        }

        List<Long> userBooths = assignedBooths.stream()
                .map(UserBoothAccess::getBoothNumber)
                .collect(Collectors.toList());

        // Check access
        if (!userBooths.containsAll(requestedBooths)) {
            throw new AccessDeniedException("User does not have access to one or more booths");
        }

        return requestedBooths;
    }

    /**
     * Validate access for SINGLE booth
     */
    public void validateBoothAccess(Long boothNumber) throws AccessDeniedException {
        validateBoothAccess(Collections.singletonList(boothNumber));
    }
}
