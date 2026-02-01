package com.appprotech.campaign.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VotingPriorityRequest {

    // Party IDs in priority order
    private Long firstPriorityPartyId;
    private Long secondPriorityPartyId;
    private Long thirdPriorityPartyId;
    private Long fourthPriorityPartyId;
    private Long fifthPriorityPartyId;
}
