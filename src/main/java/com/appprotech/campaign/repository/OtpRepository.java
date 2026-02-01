package com.appprotech.campaign.repository;


import com.appprotech.campaign.entity.OtpVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OtpRepository extends JpaRepository<OtpVerification, Long> {

    Optional<OtpVerification>
    findTopByMobileAndOtpAndVerifiedFalseOrderByIdDesc(Long mobile, String otp);

    Optional<OtpVerification>
    findTopByMobileAndVerifiedFalseOrderByIdDesc(Long mobile);

    @Query(value = """
    SELECT *
    FROM otp_verification
    WHERE mobile = :mobile
      AND user_id = :userId
      AND verified = 0
""", nativeQuery = true)
    List<OtpVerification> findAllPreviousOtps(
            @Param("mobile") Long mobile,
            @Param("userId") Long userId
    );

}
