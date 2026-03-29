package com.itwill.backend_smart_campus.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.backend_smart_campus.dto.BookDTO;
import com.itwill.backend_smart_campus.entity.Book;
import com.itwill.backend_smart_campus.entity.Borrow;
import com.itwill.backend_smart_campus.entity.UserInfo;
import com.itwill.backend_smart_campus.exception.BookNotFoundException;
import com.itwill.backend_smart_campus.exception.UnauthorizedUserException;
import com.itwill.backend_smart_campus.exception.UserNotFoundException;
import com.itwill.backend_smart_campus.repository.BookRepository;
import com.itwill.backend_smart_campus.repository.BorrowRepository;
import com.itwill.backend_smart_campus.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BorrowRepository borrowRepository;
    private final UserInfoRepository userInfoRepository;
    
    @Override
    public int insertBook(BookDTO bookDTO, String userId) throws Exception {
        UserInfo user = userInfoRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("해당 계정이 없습니다."));
        
        if(!"admin".equalsIgnoreCase(user.getUserType())) {
            throw new UnauthorizedUserException("해당 권한이 없습니다.");
        }

        Book saveBook = bookRepository.save(bookDTO.toEntity());
        return saveBook.getBookId();
    }

    @Override
    public int updateBook(BookDTO bookDTO, String userId) throws Exception {
        UserInfo user = userInfoRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("해당 계정이 없습니다."));

        if(!"admin".equalsIgnoreCase(user.getUserType())) {
            throw new UnauthorizedUserException("해당 권한이 없습니다.");
        }

        Book updateBook = bookRepository.save(bookDTO.toEntity());
        return updateBook.getBookId();
    }

    @Override
    public int deleteBookById(int bookId, String userId) throws Exception {
         UserInfo user = userInfoRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("해당 계정이 없습니다."));

        if(!"admin".equalsIgnoreCase(user.getUserType())) {
            throw new UnauthorizedUserException("해당 권한이 없습니다.");
        }
        bookRepository.deleteById(bookId);
        return bookId;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> searchBooks(String keyword) throws Exception {
       return bookRepository
            .findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(keyword, keyword)
            .stream()
            .map(bookEntity -> {
                BookDTO dto = BookDTO.toDto(bookEntity);
                Optional<Borrow> latest = borrowRepository
                    .findFirstByBook_BookIdOrderByBorrowDateDesc(bookEntity.getBookId());

                boolean isBorrowed = latest
                    .map(b -> b.getReturnDate() == null)
                    .orElse(false); 

                dto.setBookStatus(isBorrowed ? "대출중" : "대출가능");
                return dto;
            })
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> findAllBooks() throws Exception {
        return bookRepository.findAll().stream()
                                       .map(bookEntity -> {
                BookDTO dto = BookDTO.toDto(bookEntity);
                Optional<Borrow> latest = borrowRepository
                    .findFirstByBook_BookIdOrderByBorrowDateDesc(bookEntity.getBookId());

                boolean isBorrowed = latest
                    .map(b -> b.getReturnDate() == null)
                    .orElse(false); // 기록 자체가 없으면 대출가능

                dto.setBookStatus(isBorrowed ? "대출중" : "대출가능");
                return dto;
            })
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BookDTO findBookById(int bookId) throws Exception {
        Book bookEntity = bookRepository.findById(bookId)
            .orElseThrow(() -> new BookNotFoundException("해당 도서가 존재하지 않습니다."));
        BookDTO dto = BookDTO.toDto(bookEntity);
        boolean isBorrowed = borrowRepository
            .existsByBook_BookIdAndReturnDateIsNull(bookId);
        dto.setBookStatus(isBorrowed ? "대출중" : "대출가능");
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> findBookByCategory(String category) throws Exception {
        return bookRepository.findByCategory(category).stream()
                                                      .map(BookDTO::toDto)
                                                      .collect(Collectors.toList());
    }

}
