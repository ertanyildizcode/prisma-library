package com.prisma.library.library.control.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import com.prisma.library.library.control.repository.BookRepository;
import com.prisma.library.library.entity.constants.Constants;
import com.prisma.library.library.entity.model.Book;
import com.prisma.library.library.helper.CSVHelper;
import com.prisma.library.library.helper.CSVReaderHelper;
import com.prisma.library.library.interfaces.BookService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CSVHelper csvHelper;
    private final CSVReaderHelper csvReaderHelper;

    @PostConstruct
    public void initializeCsvFile() throws IOException {
        log.info("Adding book list process to db is started.");
        List<Book> bookList = executeReading();
        saveImportedBookList(bookList);
    }

    @Override
    public List<Book> executeReading() throws IOException {
        InputStream inputStream = new FileInputStream(
            Objects.requireNonNull(this.getClass().getClassLoader().getResource("books.csv")).getFile());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        List<CSVRecord> csvRecordList = csvHelper.findCsvRecords(inputStreamReader, Constants.booksCsvHeaders);
        return csvReaderHelper.readBookCsv(csvRecordList);
    }

    public void saveImportedBookList(List<Book> bookList) {
        List<Book> addBookList = new ArrayList<>();
        List<Book> updateBookList = new ArrayList<>();

        Map<ImmutablePair<String, String>, Book> bookMap = new HashMap<>();
        bookRepository.findAll()
            .forEach(book -> bookMap.put(ImmutablePair.of(book.getTitle(), book.getAuthor()), book));

        for (Book book : bookList) {
            if (!bookMap.containsKey(ImmutablePair.of(book.getTitle(), book.getAuthor()))) {
                addBookList.add(book);
            } else {
                Book updatedBook = bookMap.get(ImmutablePair.of(book.getTitle(), book.getAuthor()));
                updatedBook.setGenre(book.getGenre());
                updatedBook.setPublisher(book.getPublisher());
                updateBookList.add(updatedBook);
            }
        }
        bookRepository.saveAll(addBookList);
        bookRepository.saveAll(updateBookList);
    }

    public Book findBookByTitle(String title) {
        return bookRepository.findByTitle(title).get();
    }

    @Override
    public Set<Book> findAllBooks() {
        return new HashSet<>(bookRepository.findAll());
    }
}
