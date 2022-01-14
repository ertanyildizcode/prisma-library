package com.prisma.library.library.control.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.prisma.library.library.entity.model.Borrow;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, UUID> {

    @Query("Select b from Borrow b where b.borrowedTo < :timestamp")
    List<Borrow> findBorrowsToExpireDate(@Param("timestamp") Timestamp timestamp);

    @Query("Select b from Borrow b where b.borrowedTo > :timestamp")
    List<Borrow> findBorrowsExpired(@Param("timestamp") Timestamp timestamp);

    @Query("Select b from Borrow b where b.borrowedFrom = :timestamp")
    List<Borrow> findBorrowsByDate(@Param("timestamp") Timestamp timestamp);

    @Query(
        "Select b from Borrow b where b.borrowedFrom > :startDate and b.borrowedTo < :endDate and b.user.name = :userName and b.user.firstName = :userFirstName")
    List<Borrow> findAllBorrowsByGivenRangeAndUser(@Param("startDate") Timestamp startDate,
        @Param("endDate") Timestamp endDate, @Param("userName") String userName,
        @Param("userFirstName") String userFirstName);
}
