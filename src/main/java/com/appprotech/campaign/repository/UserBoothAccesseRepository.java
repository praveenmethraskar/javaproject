package com.appprotech.campaign.repository;

import com.appprotech.campaign.entity.UserBoothAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBoothAccesseRepository extends JpaRepository<UserBoothAccess,Long> {


    boolean existsByUserIdAndBoothNumberIn(Long userId, List<Long> boothNumbers);

    @Query("select uba.boothNumber from UserBoothAccess uba where uba.user.id = :userId")
    List<Long> accessBoothNumbeByUserId(@Param("userId") Long userId);



}
