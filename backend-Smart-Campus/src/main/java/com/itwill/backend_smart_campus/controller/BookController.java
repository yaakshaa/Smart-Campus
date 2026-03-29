package com.itwill.backend_smart_campus.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itwill.backend_smart_campus.dto.BookDTO;
import com.itwill.backend_smart_campus.dto.BorrowDTO;
import com.itwill.backend_smart_campus.dto.UserInfoResponseDto;
import com.itwill.backend_smart_campus.exception.UserNotFoundException;
import com.itwill.backend_smart_campus.service.BookService;
import com.itwill.backend_smart_campus.service.BorrowService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final BorrowService borrowService;

    @Operation(summary = "도서등록(관리자만 가능)")
    @PostMapping
    public ResponseEntity<Response> createBook(@RequestBody BookDTO bookDTO, HttpSession session) throws Exception {
        UserInfoResponseDto loginUser = (UserInfoResponseDto) session.getAttribute("sUserInfo");
        String userId = loginUser != null ? loginUser.getUserId() : null;

        if (userId == null) {
            throw new UserNotFoundException("로그인이 필요합니다.");
        }
        int bookId = bookService.insertBook(bookDTO, userId);

        Response response = new Response();
        response.setStatus(ResponseStatusCode.CREATED_BOOK);
        response.setMessage(ResponseMessage.CREATED_BOOK);
        response.setData(bookId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "도서정보수정(관리자만 가능")
    @PutMapping
    public ResponseEntity<Response> updateBook(@RequestBody BookDTO bookDTO, HttpSession session) throws Exception {
         UserInfoResponseDto loginUser = (UserInfoResponseDto) session.getAttribute("sUserInfo");
        String userId = loginUser != null ? loginUser.getUserId() : null;

        if (userId == null) {
            throw new UserNotFoundException("로그인이 필요합니다.");
        }
        int updateId = bookService.updateBook(bookDTO, userId);

        Response response = new Response();
        response.setStatus(ResponseStatusCode.UPDATED_BOOK);
        response.setMessage(ResponseMessage.UPDATED_BOOK);
        response.setData(updateId);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "도서삭제(관리자만 가능)")
    @DeleteMapping("/{bookId}")
    public ResponseEntity<Response> deleteBook(@PathVariable int bookId, HttpSession session) throws Exception {
         UserInfoResponseDto loginUser = (UserInfoResponseDto) session.getAttribute("sUserInfo");
        String userId = loginUser != null ? loginUser.getUserId() : null;

        if (userId == null) {
            throw new UserNotFoundException("로그인이 필요합니다.");
        }
        int deleteId = bookService.deleteBookById(bookId, userId);

        Response response = new Response();
        response.setStatus(ResponseStatusCode.DELETED_BOOK);
        response.setMessage(ResponseMessage.DELETED_BOOK);
        response.setData(deleteId);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "도서검색(제목/저자)")
    @GetMapping
    public ResponseEntity<Response> searchBooks(@RequestParam(value = "keyword", required = false) String keyword)
            throws Exception {
        List<BookDTO> books;

        if (keyword == null || keyword.trim().isEmpty()) {
            books = bookService.findAllBooks();
        } else {
            books = bookService.searchBooks(keyword.trim());
        }

        Response response = new Response();
        response.setStatus(ResponseStatusCode.BOOK_SUCCESS);
        response.setMessage(ResponseMessage.BOOK_SUCCESS);
        response.setData(books);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "전체도서조회")
    @GetMapping("/bookLists")
    public ResponseEntity<Response> getAllBooks() throws Exception {
        List<BookDTO> books = bookService.findAllBooks();

        Response response = new Response();
        response.setStatus(ResponseStatusCode.BOOK_SUCCESS);
        response.setMessage(ResponseMessage.BOOK_SUCCESS);
        response.setData(books);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "도서ID로 조회")
    @GetMapping("/{bookId}")
    public ResponseEntity<Response> getBookById(@PathVariable int bookId, HttpSession session) throws Exception {
        BookDTO bookDTO = bookService.findBookById(bookId);

        UserInfoResponseDto loginUser = (UserInfoResponseDto) session.getAttribute("sUserInfo");
        String userId = loginUser != null ? loginUser.getUserId() : null;

        Optional<BorrowDTO> activeOpt = (userId == null)
                ? Optional.empty()
                : borrowService.findActiveBorrow(bookId, userId);

        // 2) 버튼 조건 파생
        boolean canReturn = activeOpt.isPresent();
        Integer borrowId = activeOpt.map(BorrowDTO::getBorrowId)
                .orElse(null);

        Map<String, Object> data = new HashMap<>();
        data.put("book", bookDTO);
        data.put("canReturn", canReturn);
        data.put("borrowId", borrowId);

        Response response = new Response();
        response.setStatus(ResponseStatusCode.BOOK_SUCCESS);
        response.setMessage(ResponseMessage.BOOK_SUCCESS);
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "카테고리로 도서조회")
    @GetMapping("/category")
    public ResponseEntity<Response> getBooksByCategory(@RequestParam String category) throws Exception {
        List<BookDTO> books = bookService.findBookByCategory(category);

        Response response = new Response();
        response.setStatus(ResponseStatusCode.BOOK_SUCCESS);
        response.setMessage(ResponseMessage.BOOK_SUCCESS);
        response.setData(books);

        return ResponseEntity.ok(response);
    }
}
