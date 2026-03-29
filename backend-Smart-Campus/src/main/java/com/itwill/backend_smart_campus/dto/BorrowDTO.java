package com.itwill.backend_smart_campus.dto;

import java.time.LocalDate;

import com.itwill.backend_smart_campus.entity.Borrow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BorrowDTO {

    private int borrowId;
    private LocalDate borrowDate;
    private LocalDate expectedReturnDate;
    private LocalDate returnDate;
    private String borrowStatus;
    private UserInfoDTO user;
    private BookDTO book;

    public static BorrowDTO toDto(Borrow borrowEntity) {
        return BorrowDTO.builder()
        .borrowId(borrowEntity.getBorrowId())
        .borrowDate(borrowEntity.getBorrowDate())
        .expectedReturnDate(borrowEntity.getExpectedReturnDate())
        .returnDate(borrowEntity.getReturnDate())
        .borrowStatus(borrowEntity.getBorrowStatus())
        .book(BookDTO.toDto(borrowEntity.getBook()))
        .user(UserInfoDTO.toDto(borrowEntity.getUser()))
        .build();
    }

   public Borrow toEntity() {
     return Borrow.builder()
        .borrowDate(getBorrowDate())
        .expectedReturnDate(getExpectedReturnDate())
        .returnDate(getReturnDate())
        .borrowStatus(getBorrowStatus())
        .book(getBook() != null ? getBook().toEntity() : null)
        .user(getUser() != null ? getUser().toEntity() : null)
        .build();
   }

}
