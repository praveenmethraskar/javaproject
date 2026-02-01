    package com.appprotech.campaign.dto;

    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.util.Date;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class VoterUpdateRequest {

        private Long updateId;

        private Long originalId;

        private String epicId;

        private String name;

        private Long age;

        private String relativeName;

        private String relativeRelation;

        private String houseNumber;

        private String Gender;

        private String villageOrMaintown;

        private String mandal;

        private String revenueDivision;

        private String district;

        private String state;

        private String assemblyConstituency;

        private String parliamentary;

        private long boothNumber;

        private String pollingStation;

        private Long phoneNumber;

        private VotingPriorityRequest votingPriorityRequest;

        private boolean updateRequired;



    }
