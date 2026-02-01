package com.appprotech.campaign.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private Long phoneNumber;

    private String email;

    private String referredPerson;

    // ONLY booth numbers – not entity
    private List<Long> booths;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;
}
