package com.itwill.backend_smart_campus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.backend_smart_campus.entity.Book;

public interface BookRepository extends JpaRepository<Book, Integer>{

    // List<Book> findByTitleContaining(String keyword);
    
    // List<Book> findByAuthorContaining(String keyword);

    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String titleKeyword,String authorKeyword);

    List<Book> findByCategory(String category);
}
