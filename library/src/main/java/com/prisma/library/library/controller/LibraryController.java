package com.prisma.library.library.controller;

import java.text.ParseException;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prisma.library.library.entity.dto.BookDto;
import com.prisma.library.library.entity.dto.BooksBorrowedSearchParameter;
import com.prisma.library.library.entity.dto.UserDto;
import com.prisma.library.library.interfaces.BorrowService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/library")
public class LibraryController {

    private final BorrowService borrowService;

    @GetMapping(path = "/borrow-users",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity findBorrowedUsersAtLeastOne() {
        Set<UserDto> userDtoSet = borrowService.findBorrowedUsersAtLeastOne();

        if (userDtoSet.isEmpty()) {
            log.warn("Couldn't find user borrowed book!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(userDtoSet);
    }

    @GetMapping(path = "/non-borrow-users",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity findNonBorrowedUsers() {
        Set<UserDto> userDtoSet = borrowService.findNonBorrowedUsers();

        if (userDtoSet.isEmpty()) {
            log.warn("Couldn't find user non-borrowed book!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(userDtoSet);
    }

    @GetMapping(path = "/users-borrowed-given-date",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity findUsersBorrowedBookGivenDate(@RequestParam("date") String date) {

        Set<UserDto> userDtoSet;
        try {
            userDtoSet = borrowService.findUsersBorrowedGivenDate(date);
        } catch (ParseException parseException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Given date should be MM/dd/yyyy");
        }
        if (userDtoSet.isEmpty()) {
            log.warn("Couldn't find user for borrowed book on given date: {}!", date);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(userDtoSet);
    }

    @GetMapping(path = "/books-borrowed-user-range",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity findBooksBorrowedByUserDateRange(BooksBorrowedSearchParameter borrowedSearchParameter) {

        Set<BookDto> bookDtoSet;
        try {
            bookDtoSet = borrowService.findBooksBorrowedByUserDateRange(borrowedSearchParameter);
        } catch (ParseException parseException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Given date should be MM/dd/yyyy");
        }
        if (bookDtoSet.isEmpty()) {
            log.warn("Couldn't find books by specific user: {} {} on given date range: {} - {}!",
                borrowedSearchParameter.getFirstName(), borrowedSearchParameter.getName(),
                borrowedSearchParameter.getStartDate(), borrowedSearchParameter.getEndDate());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(bookDtoSet);
    }

    @GetMapping(path = "/available-books",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity findAvailableBooks() {

        Set<BookDto> bookDtoSet = borrowService.findAllAvailableBooks();

        if (bookDtoSet.isEmpty()) {
            log.warn("There aren't available books!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(bookDtoSet);
    }
}
