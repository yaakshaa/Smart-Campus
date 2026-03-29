package com.itwill.backend_smart_campus.service;

import java.util.List;
import java.util.Optional;

import com.itwill.backend_smart_campus.dto.BorrowDTO;

public interface BorrowService {

    int borrowBook(int bookId, String userId) throws Exception;

    int returnBook(int borrowId, String userId) throws Exception;

    List<BorrowDTO> findAllBorrows(String userId) throws Exception;

    BorrowDTO findBorrowById(int borrowId, String userId) throws Exception;

    List<BorrowDTO> findBorrowsByUserId(String targetUserId, String requesterUserId) throws Exception;

    List<BorrowDTO> findNoReturnedBorrows(String userId) throws Exception;

    Optional<BorrowDTO> findActiveBorrow(int bookId, String userId);
}
