package com.prisma.library.library.helper;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.prisma.library.library.entity.constants.Constants;
import com.prisma.library.library.entity.enums.GenderEnum;
import com.prisma.library.library.entity.model.Book;
import com.prisma.library.library.entity.model.Borrow;
import com.prisma.library.library.entity.model.User;
import com.prisma.library.library.interfaces.BookService;
import com.prisma.library.library.interfaces.CsvReader;
import com.prisma.library.library.interfaces.UserService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CSVReaderHelper implements CsvReader {

    private final UserService userService;
    private final BookService bookService;

    public CSVReaderHelper(@Lazy UserService userService, @Lazy BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
    }

    @Override
    public List<User> readUserCsv(List<CSVRecord> csvRecordList) {
        List<User> userList = new ArrayList<>();
        csvRecordList.forEach(csvRecord -> {
            try {
                User user = new User();
                user.setName(csvRecord.get(Constants.USER_NAME));
                user.setFirstName(csvRecord.get(Constants.USER_FIRST_NAME));
                String memberSince = csvRecord.get(Constants.USER_MEMBER_SINCE);
                user.setMemberSince(
                    memberSince.isEmpty() ? null : Timestamp.from(Constants.dateFormat.parse(memberSince).toInstant()));
                String memberTill = csvRecord.get(Constants.USER_MEMBER_TILL);
                user.setMemberTill(
                    memberTill.isEmpty() ? null : Timestamp.from(Constants.dateFormat.parse(memberTill).toInstant()));
                user.setGender(GenderEnum.getByValue(csvRecord.get(Constants.USER_GENDER)));
                userList.add(user);
            } catch (ParseException e) {
                log.error("Given date string could not parsed");
            }

        });
        return userList;
    }

    @Override
    public List<Book> readBookCsv(List<CSVRecord> csvRecordList) {
        List<Book> bookList = new ArrayList<>();
        csvRecordList.forEach(csvRecord -> {
            Book book = new Book();
            String author = csvRecord.get(Constants.AUTHOR);
            String title = csvRecord.get(Constants.TITLE);
            String genre = csvRecord.get(Constants.GENRE);
            String publisher = csvRecord.get(Constants.PUBLISHER);
            if (author.isEmpty() || title.isEmpty() || genre.isEmpty() || publisher.isEmpty()) {
                return;
            }
            book.setAuthor(author);
            book.setTitle(title);
            book.setGenre(genre);
            book.setPublisher(publisher);
            bookList.add(book);
        });
        return bookList;
    }

    @Override
    public List<Borrow> readBorrowCsv(List<CSVRecord> csvRecordList) {
        List<Borrow> borrowList = new ArrayList<>();
        csvRecordList.forEach(csvRecord -> {
            try {
                Borrow borrow = new Borrow();

                String borrower = csvRecord.get(Constants.BORROWER);
                String name = borrower.split(",")[0];
                String firstName = borrower.split(",")[1];
                User user = userService.findUserByNameAndFirstName(name, firstName);
                borrow.setUser(user);

                Book book = bookService.findBookByTitle(csvRecord.get(Constants.BORROW_BOOK));
                borrow.setBook(book);

                String borrowedFrom = csvRecord.get(Constants.BORROW_FROM);
                borrow.setBorrowedFrom(Timestamp.from(Constants.dateFormat.parse(borrowedFrom).toInstant()));

                String borrowedTo = csvRecord.get(Constants.BORROW_TO);
                borrow.setBorrowedTo(Timestamp.from(Constants.dateFormat.parse(borrowedTo).toInstant()));

                borrowList.add(borrow);
            } catch (ParseException e) {
                log.error("Given date string could not parsed");
            }
        });
        return borrowList;
    }
}
