package com.itwill.backend_smart_campus.dto;

import com.itwill.backend_smart_campus.entity.Book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDTO {

    private int bookId;
    private String title;
    private String author;
    private String isbn;
    private String category;
    private String publisher;
    private int publisherYear;
    private int stock;
    private String bookImg;
    private String bookStatus;

    public static BookDTO toDto(Book bookEntity) {
        return BookDTO.builder()
        .bookId(bookEntity.getBookId())
        .title(bookEntity.getTitle())
        .author(bookEntity.getAuthor())
        .isbn(bookEntity.getIsbn())
        .category(bookEntity.getCategory())
        .publisher(bookEntity.getPublisher())
        .publisherYear(bookEntity.getPublisherYear())
        .stock(bookEntity.getStock())
        .bookImg(bookEntity.getBookImg())
        .build();
    }

    public Book toEntity() {
        return Book.builder()    
        .bookId(getBookId())
        .title(getTitle())
        .author(getAuthor())
        .isbn(getIsbn())
        .category(getCategory())
        .publisher(getPublisher())
        .publisherYear(getPublisherYear())
        .stock(getStock())
        .bookImg(getBookImg())
        .build();
    }
}
