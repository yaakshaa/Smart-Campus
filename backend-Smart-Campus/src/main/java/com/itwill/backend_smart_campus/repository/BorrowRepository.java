package com.itwill.backend_smart_campus.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.backend_smart_campus.entity.Borrow;

public interface BorrowRepository extends JpaRepository<Borrow, Integer> {

    int countByUser_UserIdAndBorrowStatus(String userId, String borrowStatus);

    List<Borrow> findByUser_UserId(String userId);

    List<Borrow> findByBorrowStatus(String borrowStatus);

    boolean existsByBook_BookIdAndReturnDateIsNull(int bookId);

    Optional<Borrow> findFirstByBook_BookIdOrderByBorrowDateDesc(int bookId);

    Optional<Borrow> findByBook_BookIdAndUser_UserIdAndReturnDateIsNull(
            int bookId, String userId);
}