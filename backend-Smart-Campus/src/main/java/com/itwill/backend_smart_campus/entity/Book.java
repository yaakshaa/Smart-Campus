package com.itwill.backend_smart_campus.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq_gen")
    @SequenceGenerator(name = "book_seq_gen", sequenceName = "book_seq", allocationSize = 1)
    @Column(name = "book_id")
    private Integer bookId;

    @Column(name = "book_title", nullable = false)
    private String title;

    @Column(name = "book_author", nullable = false)
    private String author;

    @Column(name = "book_isbn", nullable = false, unique = true)
    private String isbn;

    @Column(name = "book_category")
    private String category;

    @Column(name = "book_publisher")
    private String publisher;

    @Column(name = "book_publisher_year")
    private int publisherYear;

    @Column(name = "book_stock")
    private int stock;

    @Column(name = "book_img")
    private String bookImg;

    @Column(name = "book_status")
    private String bookStatus;
}


