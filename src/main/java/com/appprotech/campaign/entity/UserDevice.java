package com.appprotech.campaign.entity;


import com.appprotech.campaign.enums.DeviceType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_devices")
public class UserDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, unique = true)
    private String deviceId;   // UUID from client

    @Enumerated(EnumType.STRING)
    private DeviceType deviceType; // ANDROID, IOS, WEB

    private String platform;       // Android, iOS, Windows
    private String osVersion;
    private String browser;
    private String appVersion;

    private String ipAddress;
    private String location;

    private boolean isActive = true;
    private boolean isTrusted = false;

    private int loginCount = 0;

    private LocalDateTime lastLoginAt;
    private LocalDateTime lastLogoutAt;



}
