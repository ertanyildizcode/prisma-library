package com.prisma.library.library.interfaces;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.prisma.library.library.entity.model.Book;
import com.prisma.library.library.entity.model.Borrow;
import com.prisma.library.library.entity.model.User;

public interface CsvReader {

    List<User> readUserCsv(List<CSVRecord> csvRecordList);

    List<Book> readBookCsv(List<CSVRecord> csvRecordList);

    List<Borrow> readBorrowCsv(List<CSVRecord> csvRecordList);
}
