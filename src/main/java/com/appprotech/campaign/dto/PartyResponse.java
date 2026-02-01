package com.appprotech.campaign.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartyResponse {

    private Long id;
    private String partyName;
    private String contestedName;
    private String partyPresident;
}
