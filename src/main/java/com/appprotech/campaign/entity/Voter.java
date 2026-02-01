package com.appprotech.campaign.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="voter_table")
public class Voter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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

    private Date createdDate;

    private String NoAndNameOfSectionsInThePart;

    private String addressOfPollingStation;

    private String pincode;
}
