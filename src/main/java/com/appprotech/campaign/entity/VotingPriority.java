        package com.appprotech.campaign.entity;


        import jakarta.persistence.*;
        import lombok.AllArgsConstructor;
        import lombok.Builder;
        import lombok.Data;
        import lombok.NoArgsConstructor;

        import java.time.LocalDateTime;

        @Entity
        @Table(name = "voting_priority")
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public class VotingPriority {

            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            private Long id;

            @OneToOne(fetch = FetchType.LAZY)
            @JoinColumn(name = "updated_voter_id", nullable = false, unique = true)
            private UpdatedVoter updatedVoter;

            @ManyToOne(fetch = FetchType.LAZY)
            @JoinColumn(name = "first_priority_party_id")
            private Party firstPriority;

            @ManyToOne(fetch = FetchType.LAZY)
            @JoinColumn(name = "second_priority_party_id")
            private Party secondPriority;

            @ManyToOne(fetch = FetchType.LAZY)
            @JoinColumn(name = "third_priority_party_id")
            private Party thirdPriority;

            @ManyToOne(fetch = FetchType.LAZY)
            @JoinColumn(name = "fourth_priority_party_id")
            private Party fourthPriority;

            @ManyToOne(fetch = FetchType.LAZY)
            @JoinColumn(name = "fifth_priority_party_id")
            private Party fifthPriority;

            @Builder.Default
            private LocalDateTime localDateTime = LocalDateTime.now();


        }


