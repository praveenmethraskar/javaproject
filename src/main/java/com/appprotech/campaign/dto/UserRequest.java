    package com.appprotech.campaign.dto;

    import com.appprotech.campaign.entity.BoothNumbers;
    import jakarta.validation.constraints.*;
    import lombok.Data;

    import java.util.List;

    @Data
    public class UserRequest {

        @NotBlank
        private String username;

        @NotBlank
        private String password;

        private String firstName;
        private String lastName;

        @NotNull
        private Long phoneNumber;

        @Email
        @NotBlank
        private String email;

        private String referredPerson;

        private List<Long> booths;
    }
