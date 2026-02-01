package com.appprotech.campaign.repository;

import com.appprotech.campaign.entity.BoothNumbers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoothNumbersRepository extends JpaRepository<BoothNumbers,Long> {

    @Query(value = "SELECT booth_number FROM booth_numbers", nativeQuery = true)
    List<Long> availableBooths();

}
