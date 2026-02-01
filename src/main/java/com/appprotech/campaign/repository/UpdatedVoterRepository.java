package com.appprotech.campaign.repository;

import com.appprotech.campaign.entity.UpdatedVoter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpdatedVoterRepository extends JpaRepository<UpdatedVoter, Long> {
}
