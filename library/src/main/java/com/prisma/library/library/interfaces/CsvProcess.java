package com.prisma.library.library.interfaces;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

public interface CsvProcess {

    List<CSVRecord> findCsvRecords(InputStreamReader inputStreamReader, String[] headers) throws IOException;
}
