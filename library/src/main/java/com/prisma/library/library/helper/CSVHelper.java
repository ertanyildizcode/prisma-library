package com.prisma.library.library.helper;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import com.prisma.library.library.interfaces.CsvProcess;

@Component
public class CSVHelper implements CsvProcess {

    @Override
    public List<CSVRecord> findCsvRecords(InputStreamReader inputStreamReader, String[] headers) throws IOException {
        return CSVFormat.DEFAULT.withHeader(headers)
            .withSkipHeaderRecord()
            .withDelimiter(',')
            .withAllowMissingColumnNames()
            .withIgnoreHeaderCase()
            .parse(inputStreamReader)
            .getRecords();
    }
}
