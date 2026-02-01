            package com.appprotech.campaign.entity;

            import jakarta.persistence.*;
            import lombok.AllArgsConstructor;
            import lombok.Builder;
            import lombok.Data;
            import lombok.NoArgsConstructor;

            import java.time.LocalDateTime;

            @Entity
            @Data
            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Table(
                    name = "user_booth_access",
                    uniqueConstraints = {
                            @UniqueConstraint(columnNames = {"user_id", "booth_number"})
                    }
            )

            public class UserBoothAccess {

                @Id
                @GeneratedValue(strategy = GenerationType.IDENTITY)
                private Long id;

                @Column(name = "booth_number", nullable = false)
                private long boothNumber;

                @ManyToOne(fetch = FetchType.LAZY)
                @JoinColumn(name = "user_id", nullable = false)
                private User user;

                private LocalDateTime createdDate;

                private LocalDateTime updatedDate;


                private String assemblyConstituency;

                private String parliamentary;
            }
