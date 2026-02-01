    package com.appprotech.campaign.dto;

    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class VoterUpdateResponse {

        private Long updatedId;

        private Long originalId;

        private String epicId;

        private String name;

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

        private VotingPriorityResponse votingPriorityResponse;



    }
