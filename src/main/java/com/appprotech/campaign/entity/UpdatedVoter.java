package com.appprotech.campaign.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
    @Table(name="duplicate_voter_table")
public class UpdatedVoter {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long duplicateId;

    private long originalId;

    private long extractId;

    private String epicId;

    private String name;

    private String relativeName;

    private String relativeRelation;

    private String houseNumber;

    private String Gender;

    private long age;

    private String villageOrMaintown;

    private String mandal;

    private String revenueDivision;

    private String district;

    private String state;

    private String policeStation;

    private String postOffice;

    private String assemblyConstituency;

    private String parliamentary;

    private long boothNumber;

    private String pollingStation;


    private String NoAndNameOfSectionsInThePart;

    private String addressOfPollingStation;

    private long phoneNumber;

    @OneToOne(mappedBy = "updatedVoter", cascade = CascadeType.ALL)
    private VotingPriority votingPriority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", updatable = false)
    private User createdBy;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    private LocalDateTime updatedDate;






}
