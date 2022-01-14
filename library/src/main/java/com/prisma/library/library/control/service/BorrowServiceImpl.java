package com.prisma.library.library.control.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import com.prisma.library.library.control.mapper.BookMapper;
import com.prisma.library.library.control.mapper.UserMapper;
import com.prisma.library.library.control.repository.BorrowRepository;
import com.prisma.library.library.entity.constants.Constants;
import com.prisma.library.library.entity.dto.BookDto;
import com.prisma.library.library.entity.dto.BooksBorrowedSearchParameter;
import com.prisma.library.library.entity.dto.UserDto;
import com.prisma.library.library.entity.model.Book;
import com.prisma.library.library.entity.model.Borrow;
import com.prisma.library.library.entity.model.User;
import com.prisma.library.library.helper.CSVHelper;
import com.prisma.library.library.helper.CSVReaderHelper;
import com.prisma.library.library.interfaces.BookService;
import com.prisma.library.library.interfaces.BorrowService;
import com.prisma.library.library.interfaces.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BorrowServiceImpl implements BorrowService {

    private final BorrowRepository borrowRepository;
    private final UserService userService;
    private final BookService bookService;
    private final CSVHelper csvHelper;
    private final CSVReaderHelper csvReaderHelper;

    @PostConstruct
    public void initializeCsvFile() throws IOException {
        log.info("Adding borrow list process to db is started.");
        List<Borrow> borrowList = executeReading();
        saveImportedBorrowList(borrowList);
    }

    @Override
    public List<Borrow> executeReading() throws IOException {
        InputStream inputStream = new FileInputStream(
            Objects.requireNonNull(this.getClass().getClassLoader().getResource("borrowed.csv")).getFile());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        List<CSVRecord> csvRecordList = csvHelper.findCsvRecords(inputStreamReader, Constants.borrowCsvHeaders);
        return csvReaderHelper.readBorrowCsv(csvRecordList);
    }

    public void saveImportedBorrowList(List<Borrow> borrowList) {
        List<Borrow> addBorrowList = new ArrayList<>();
        List<Borrow> updateBorrowList = new ArrayList<>();

        Map<ImmutablePair<UUID, UUID>, Borrow> borrowMap = new HashMap<>();
        borrowRepository.findAll()
            .forEach(
                borrow -> borrowMap.put(ImmutablePair.of(borrow.getUser().getId(), borrow.getBook().getId()), borrow));

        for (Borrow borrow : borrowList) {
            if (!borrowMap.containsKey(ImmutablePair.of(borrow.getUser().getId(), borrow.getBook().getId()))) {
                addBorrowList.add(borrow);
            } else {
                Borrow updatedBorrow = borrowMap.get(
                    ImmutablePair.of(ImmutablePair.of(borrow.getUser().getId(), borrow.getBook().getId())));
                updatedBorrow.setBorrowedTo(borrow.getBorrowedTo());
                updatedBorrow.setBorrowedFrom(borrow.getBorrowedFrom());
                updateBorrowList.add(updatedBorrow);
            }
        }
        borrowRepository.saveAll(addBorrowList);
        borrowRepository.saveAll(updateBorrowList);
    }

    public Set<UserDto> findBorrowedUsersAtLeastOne() {
        return borrowRepository.findAll()
            .stream()
            .map(borrow -> UserMapper.getInstance().userToUserDto(borrow.getUser()))
            .collect(Collectors.toSet());
    }

    public Set<UserDto> findNonBorrowedUsers() {

        Timestamp currentTime = Timestamp.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        return borrowRepository.findBorrowsToExpireDate(currentTime)
            .stream()
            .filter(borrow -> borrow.getUser().getMemberTill() == null)
            .map(borrow -> UserMapper.getInstance().userToUserDto(borrow.getUser()))
            .collect(Collectors.toSet());
    }

    public Set<UserDto> findUsersBorrowedGivenDate(String date) throws ParseException {
        Timestamp formattedDate = Timestamp.from(Constants.dateFormat.parse(date).toInstant());

        return borrowRepository.findBorrowsByDate(formattedDate)
            .stream()
            .map(borrow -> UserMapper.getInstance().userToUserDto(borrow.getUser()))
            .collect(Collectors.toSet());
    }

    public Set<BookDto> findBooksBorrowedByUserDateRange(BooksBorrowedSearchParameter booksBorrowedSearchParameter)
        throws ParseException {

        Timestamp startDate =
            Timestamp.from(Constants.dateFormat.parse(booksBorrowedSearchParameter.getStartDate()).toInstant());
        Timestamp endDate =
            Timestamp.from(Constants.dateFormat.parse(booksBorrowedSearchParameter.getEndDate()).toInstant());
        User user = userService.findUserByNameAndFirstName(booksBorrowedSearchParameter.getName(),
            booksBorrowedSearchParameter.getFirstName());

        return borrowRepository.findAllBorrowsByGivenRangeAndUser(startDate, endDate, user.getName(),
            user.getFirstName())
            .stream()
            .map(borrow -> BookMapper.getInstance().bookToBookDto(borrow.getBook()))
            .collect(Collectors.toSet());
    }

    public Set<BookDto> findAllAvailableBooks() {
        Timestamp currentTime = Timestamp.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));

        Set<Book> allBooks = bookService.findAllBooks();
        Set<Book> allBorrowedBooks =
            borrowRepository.findAll().stream().map(Borrow::getBook).collect(Collectors.toSet());

        Set<Book> borrowedAndNonExpireBooks = borrowRepository.findBorrowsToExpireDate(currentTime)
            .stream()
            .map(Borrow::getBook)
            .collect(Collectors.toSet());

        Set<Book> expiredBooks =
            borrowRepository.findBorrowsExpired(currentTime).stream().map(Borrow::getBook).collect(Collectors.toSet());

        borrowedAndNonExpireBooks.removeAll(expiredBooks);

        Set<BookDto> resultSet = borrowedAndNonExpireBooks.stream()
            .map(book -> BookMapper.getInstance().bookToBookDto(book))
            .collect(Collectors.toSet());

        allBooks.removeAll(allBorrowedBooks);

        resultSet.addAll(
            allBooks.stream().map(book -> BookMapper.getInstance().bookToBookDto(book)).collect(Collectors.toSet()));

        return resultSet;
    }
}
