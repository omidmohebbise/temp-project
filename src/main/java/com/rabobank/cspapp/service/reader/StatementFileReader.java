package com.rabobank.cspapp.service.reader;

import com.rabobank.cspapp.model.Statement;
import com.rabobank.cspapp.service.reader.csv.CSVStatementReader;
import com.rabobank.cspapp.service.reader.xml.XMLStatementReader;
import com.rabobank.cspapp.service.validator.ValidFileFormat;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class StatementFileReader {
    private final StatementReader csvStatementReader;
    private final StatementReader xmlStatementReader;


    public StatementFileReader(CSVStatementReader csvStatementReader, XMLStatementReader xmlStatementReader) {
        this.csvStatementReader = csvStatementReader;
        this.xmlStatementReader = xmlStatementReader;
    }

    /**
     * Reads the provided content and processes it according to the specified file format.
     *
     * @param content The content to be read and processed.
     * @param format  The file format of the content (e.g., {@link ValidFileFormat#CSV} or
     *                {@link ValidFileFormat#XML}).
     * @return A list of {@link Statement} objects parsed from the content.
     */
    public List<Statement> read(String content, ValidFileFormat format) {
        List<Statement> statements;
        if (Objects.equals(format, ValidFileFormat.XML)) {
            statements = xmlStatementReader.read(content);
        } else {
            statements = csvStatementReader.read(content);
        }
        return statements;
    }

}
