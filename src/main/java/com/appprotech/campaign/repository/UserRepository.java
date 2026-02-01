package com.appprotech.campaign.repository;

import com.appprotech.campaign.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

//    @Query(
//            value = "SELECT * FROM user_table WHERE phone_number = :phoneNumber",
//            nativeQuery = true
//    )
//    User findByPhoneNumber(@Param("phoneNumber") Long phoneNumber);

    Optional<User> findByPhoneNumber(Long phoneNumber);



//    Optional<User> findByPhoneNumber(Long phoneNumber);


    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    @Query("""
    SELECT u FROM User u
    LEFT JOIN FETCH u.assignedBoothNumbers
    WHERE u.id = :userId
""")
    Optional<User> findByIdWithBooths(@Param("userId") Long userId);

    @Query("""
        SELECT u
        FROM User u
        LEFT JOIN FETCH u.assignedBoothNumbers
        WHERE u.id = :id
    """)
    Optional<User> findWithBooths(@Param("id") Long id);



}
