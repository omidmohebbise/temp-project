package com.rabobank.cspapp.service.reader;

import com.rabobank.cspapp.service.reader.csv.CSVStatementReader;
import com.rabobank.cspapp.service.reader.xml.XMLStatementReader;
import com.rabobank.cspapp.service.validator.ValidFileFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class StatementFileReaderTest {

    @Mock
    private CSVStatementReader csvStatementReader;

    @Mock
    private XMLStatementReader xmlStatementReader;

    @InjectMocks
    private StatementFileReader statementFileReader;



    @Test
    public void testReadCsv() {
        String csvContent = "";
        ValidFileFormat format = ValidFileFormat.CSV;

        statementFileReader.read(csvContent, format);

        // Verify that the CSVStatementReader's read method was called once
        verify(csvStatementReader, times(1)).read(anyString());

        // Verify that the XMLStatementReader's read method was not called
        verify(xmlStatementReader, times(0)).read(anyString());
    }

    @Test
    public void testReadXml() {
        String xmlContent = "";
        ValidFileFormat format = ValidFileFormat.XML;

        statementFileReader.read(xmlContent, format);

        // Verify that the XMLStatementReader's read method was called once
        verify(xmlStatementReader, times(1)).read(anyString());

        // Verify that the CSVStatementReader's read method was not called
        verify(csvStatementReader, times(0)).read(anyString());
    }

}