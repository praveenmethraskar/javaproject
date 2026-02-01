package com.appprotech.campaign.entity;


import com.appprotech.campaign.enums.LoginFailureReason;
import com.appprotech.campaign.enums.LoginStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "login_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class    LoginDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nullable → failed login may not map to a user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Always available (entered phone/email)
    @Column(nullable = false)
    private Long phoneNumber;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "device_id")
    private DeviceAttributes deviceAttributes;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoginStatus status;// SUCCESS / FAILED



    @Enumerated(EnumType.STRING)
    @Column(name = "failure_reason", length = 50)
    private LoginFailureReason failureReason;


    @Column(nullable = false)
    private LocalDateTime loginAt;

    private String ipAddress;

    @Column(length = 1000)
    private String userAgent;

    private boolean suspicious = false;
}
