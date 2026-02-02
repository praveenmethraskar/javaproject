package com.appprotech.campaign.repository;

import com.appprotech.campaign.entity.Voter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface VoterRepository extends JpaRepository<Voter, Long> {

    @Query("SELECT v.epicId FROM Voter v WHERE v.epicId IN :epicIds")
    Set<String> findExistingEpicIds(@Param("epicIds") Set<String> epicIds);

    Optional<Voter> findByEpicId(String epicId);


    @Query(value = "SELECT * FROM voter_table WHERE booth_number IN (:boothNumbers)", nativeQuery = true)
    List<Voter> findAllVoters(@Param("boothNumbers") List<Long> boothNumbers);


    @Query(value = "SELECT * FROM voter_table WHERE booth_number=:boothNumbers", nativeQuery = true)
    List<Voter> findAllVotersbyboothNumber(@Param("boothNumbers") Long boothNumbers);


}
