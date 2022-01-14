package com.prisma.library.library.control.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.prisma.library.library.entity.dto.BookDto;
import com.prisma.library.library.entity.model.Book;

class BookMapperTest {

    @Test
    void bookToBookDto() {
        //given
        Book book = new Book();
        book.setPublisher("test_publisher");
        book.setGenre("test_genre");
        book.setAuthor("test_author");
        book.setTitle("test_title");

        BookDto bookDto = new BookDto();
        bookDto.setTitle("test_title");
        bookDto.setPublisher("test_publisher");
        bookDto.setGenre("test_genre");
        bookDto.setAuthor("test_author");
        //
        BookDto result = BookMapper.getInstance().bookToBookDto(book);

        assertNotNull(result);
        assertEquals(bookDto, result);
    }
}
