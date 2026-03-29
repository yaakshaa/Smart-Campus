package com.itwill.backend_smart_campus.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.backend_smart_campus.dto.BorrowDTO;
import com.itwill.backend_smart_campus.entity.Book;
import com.itwill.backend_smart_campus.entity.Borrow;
import com.itwill.backend_smart_campus.entity.UserInfo;
import com.itwill.backend_smart_campus.exception.BorrowNotFoundException;
import com.itwill.backend_smart_campus.exception.UnauthorizedUserException;
import com.itwill.backend_smart_campus.exception.UserNotFoundException;
import com.itwill.backend_smart_campus.repository.BookRepository;
import com.itwill.backend_smart_campus.repository.BorrowRepository;
import com.itwill.backend_smart_campus.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BorrowServiceImpl implements BorrowService {
    private final BorrowRepository borrowRepository;
    private final BookRepository bookRepository;
    private final UserInfoRepository userInfoRepository;

    @Override
    public int borrowBook(int bookId, String userId) throws Exception {
        UserInfo user = userInfoRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("해당 계정이 없습니다."));

        if (!"student".equalsIgnoreCase(user.getUserType())) {
            throw new UnauthorizedUserException("학생만 도서를 대출할 수 있습니다.");
        }

        Book bookRef = bookRepository.getReferenceById(bookId);
        UserInfo userRef = userInfoRepository.getReferenceById(userId);

        boolean already = borrowRepository.existsByBook_BookIdAndReturnDateIsNull(bookId);

        if (already) { // 같은 책을 아직 안 돌려줬다!
            throw new IllegalStateException("이미 대출 중인 책입니다.");
        }

        int currentCount = borrowRepository.countByUser_UserIdAndBorrowStatus(userId, "대출중");
        if (currentCount >= 3) {
            throw new BorrowNotFoundException("최대 3권까지 대출할 수 있습니다.");
        }

        Borrow borrow = Borrow.builder()
                .book(bookRef) // FK 채움
                .user(userRef)
                .borrowDate(LocalDate.now())
                .expectedReturnDate(LocalDate.now().plusWeeks(1))
                .borrowStatus("대출중")
                .build();

        return borrowRepository.save(borrow).getBorrowId();
    }

    @Override
    public int returnBook(int borrowId, String userId) throws Exception {
        UserInfo user = userInfoRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("해당 계정이 없습니다."));

        if (!"student".equalsIgnoreCase(user.getUserType())) {
            throw new UnauthorizedUserException("학생만 도서를 반납할 수 있습니다.");
        }

        Borrow borrowed = borrowRepository.findById(borrowId)
                .orElseThrow(() -> new BorrowNotFoundException("해당 대출 정보를 찾을 수 없습니다."));

        if (!borrowed.getUser().getUserId().equals(userId)) {
            throw new UnauthorizedUserException("본인이 대출한 도서만 반납할 수 있습니다.");
        }

        borrowed.setBorrowStatus("대출가능");
        borrowed.setReturnDate(LocalDate.now());

        Borrow updateBorrow = borrowRepository.save(borrowed);
        return updateBorrow.getBorrowId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BorrowDTO> findAllBorrows(String userId) throws Exception {
        UserInfo user = userInfoRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("해당 계정이 없습니다."));

        if (!"admin".equalsIgnoreCase(user.getUserType())) {
            throw new UnauthorizedUserException("해당 권한이 없습니다.");
        }
        return borrowRepository.findAll().stream()
                .map(BorrowDTO::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BorrowDTO findBorrowById(int borrowId, String userId) throws Exception {
        UserInfo user = userInfoRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("해당 계정이 없습니다."));

        if (!"admin".equals(user.getUserType())) {
            throw new UnauthorizedUserException("해당 권한이 없습니다.");
        }

        Borrow borrow = borrowRepository.findById(borrowId)
                .orElseThrow(() -> new RuntimeException("해당 대출 정보를 찾을 수 없습니다."));
        return BorrowDTO.toDto(borrow);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BorrowDTO> findBorrowsByUserId(String targetUserId, String requesterUserId) throws Exception {
        UserInfo requester = userInfoRepository.findById(requesterUserId)
                .orElseThrow(() -> new UserNotFoundException("해당 계정이 없습니다."));

        if ("admin".equalsIgnoreCase(requester.getUserType())) {
            return borrowRepository.findByUser_UserId(targetUserId).stream()
                    .map(BorrowDTO::toDto)
                    .collect(Collectors.toList());
        }

        if ("student".equalsIgnoreCase(requester.getUserType()) && targetUserId.equals(requesterUserId)) {
            return borrowRepository.findByUser_UserId(targetUserId).stream()
                    .map(BorrowDTO::toDto)
                    .collect(Collectors.toList());
        }
        throw new UnauthorizedUserException("해당 권한이 없습니다.");
    }

    @Override
    @Transactional(readOnly = true)
    public List<BorrowDTO> findNoReturnedBorrows(String userId) throws Exception {
        UserInfo user = userInfoRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("해당 계정이 없습니다."));

        if (!"admin".equalsIgnoreCase(user.getUserType())) {
            throw new UnauthorizedUserException("해당 권한이 없습니다.");
        }

        return borrowRepository.findByBorrowStatus("대출중").stream()
                .map(BorrowDTO::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BorrowDTO> findActiveBorrow(int bookId, String userId) {
        return borrowRepository
                .findByBook_BookIdAndUser_UserIdAndReturnDateIsNull(bookId, userId)
                .map(BorrowDTO::toDto);
    }

}
