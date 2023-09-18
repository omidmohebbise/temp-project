package com.rabobank.cspapp.service.reader.csv;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.rabobank.cspapp.model.Statement;
import com.rabobank.cspapp.service.reader.StatementReader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CSVStatementReader implements StatementReader {

    /**
     * Reads CSV data and converts it into a list of {@link Statement} objects.
     *
     * @param statements The CSV data to be processed.
     * @return A list of {@link Statement} objects representing the parsed CSV data.
     * @throws IllegalArgumentException If an error occurs while processing the CSV data.
     */
    @Override
    public List<Statement> read(String statements) {
        CsvMapper csvMapper = CsvMapper.builder()
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
                .build();
        CsvSchema bootstrap = csvMapper.schema().withHeader();
        try {
            MappingIterator<Statement> mappingIterator = csvMapper.readerFor(Statement.class).with(bootstrap).readValues(statements);
            return mappingIterator.readAll();
        } catch (IOException e) {
            throw new IllegalArgumentException("An error occurred while processing the CSV data. Please ensure the data is " +
                    "correctly formatted and retry.", e);
        }
    }

}

