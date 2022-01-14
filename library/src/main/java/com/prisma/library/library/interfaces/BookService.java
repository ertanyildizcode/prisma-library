package com.prisma.library.library.interfaces;

import java.util.List;
import java.util.Set;

import com.prisma.library.library.entity.model.Book;

public interface BookService extends CsvReadingExecution<List<Book>> {

    void saveImportedBookList(List<Book> bookList);

    Book findBookByTitle(String title);

    Set<Book> findAllBooks();
}
