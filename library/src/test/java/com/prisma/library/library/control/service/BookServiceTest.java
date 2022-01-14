package com.prisma.library.library.control.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prisma.library.library.control.repository.BookRepository;
import com.prisma.library.library.entity.model.Book;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @Test
    void process_saveImportedBookList_whenParamsValid() {
        //given
        List<Book> bookList = new ArrayList<>();
        Book book = new Book();
        book.setPublisher("test_publisher");
        book.setGenre("test_genre");
        book.setAuthor("test_author");
        book.setTitle("test_title");
        bookList.add(book);

        //when
        when(bookRepository.findAll()).thenReturn(bookList);

        bookService.saveImportedBookList(bookList);

        verify(bookRepository, times(1)).findAll();
        verify(bookRepository, times(2)).saveAll(any());

    }

    @Test
    void process_findBookByTitle_whenParamsValid() {
        //given
        Book book = new Book();
        book.setPublisher("test_publisher");
        book.setGenre("test_genre");
        book.setAuthor("test_author");
        book.setTitle("test_title");

        Optional<Book> optionalBook = Optional.of(book);

        //when
        when(bookRepository.findByTitle("test_title")).thenReturn(optionalBook);

        Book result = bookService.findBookByTitle("test_title");

        assertEquals(book, result);
    }
}
