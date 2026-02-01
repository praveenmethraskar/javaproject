package com.appprotech.campaign.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="party")
@Entity
public class Party {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String partyName;

    private String contestedName;

    private String partyPresident;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;
}
