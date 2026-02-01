package com.appprotech.campaign.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VotingPriorityResponse {

    private Long id;
    private PartyResponse firstPriority;
    private PartyResponse secondPriority;
    private PartyResponse thirdPriority;
    private PartyResponse fourthPriority;
    private PartyResponse fifthPriority;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
