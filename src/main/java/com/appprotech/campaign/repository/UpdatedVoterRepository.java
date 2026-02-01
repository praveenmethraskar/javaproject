package com.appprotech.campaign.repository;

import com.appprotech.campaign.entity.UpdatedVoter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UpdatedVoterRepository extends JpaRepository<UpdatedVoter, Long> {

@Query(value="select * from duplicate_voter_table where epic_id=:aLong",nativeQuery = true)
    Optional<UpdatedVoter> findByEpicID(String aLong);
}
