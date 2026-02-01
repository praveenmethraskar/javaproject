package com.appprotech.campaign.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="booth_numbers")
public class BoothNumbers {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long boothNumber;

    private String assemblyConstituency;

    private String parliamentary;

}
