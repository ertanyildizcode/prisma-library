package com.prisma.library.library.interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface CsvReadingExecution<T>{

    T executeReading() throws IOException;
}
