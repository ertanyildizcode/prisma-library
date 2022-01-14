package com.prisma.library.library.interfaces;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

import com.prisma.library.library.entity.dto.BookDto;
import com.prisma.library.library.entity.dto.BooksBorrowedSearchParameter;
import com.prisma.library.library.entity.dto.UserDto;
import com.prisma.library.library.entity.model.Borrow;

public interface BorrowService extends CsvReadingExecution<List<Borrow>> {

    void saveImportedBorrowList(List<Borrow> borrowList);

    Set<UserDto> findBorrowedUsersAtLeastOne();

    Set<UserDto> findNonBorrowedUsers();

    Set<UserDto> findUsersBorrowedGivenDate(String date) throws ParseException;

    Set<BookDto> findBooksBorrowedByUserDateRange(BooksBorrowedSearchParameter booksBorrowedSearchParameter)
        throws ParseException;

    Set<BookDto> findAllAvailableBooks();
}
