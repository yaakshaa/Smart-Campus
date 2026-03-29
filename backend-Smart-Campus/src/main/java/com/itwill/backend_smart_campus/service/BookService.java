package com.itwill.backend_smart_campus.service;

import java.util.List;

import com.itwill.backend_smart_campus.dto.BookDTO;

public interface BookService {

    int insertBook(BookDTO bookDTO, String userId) throws Exception;

    int updateBook(BookDTO bookDTO, String userId) throws Exception;
    
    int deleteBookById(int bookId, String userId) throws Exception;
    
    // List<BookDTO> searchBooks(String filter, String keyword) throws Exception;
    List<BookDTO> searchBooks(String keyword) throws Exception;

    List<BookDTO> findAllBooks() throws Exception;
    
    BookDTO findBookById(int bookId) throws Exception;

    List<BookDTO> findBookByCategory(String category) throws Exception;
}
