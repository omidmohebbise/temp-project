package com.rabobank.cspapp.service.reader;

import com.rabobank.cspapp.TestUtil;
import com.rabobank.cspapp.service.reader.csv.CSVStatementReader;
import com.rabobank.cspapp.service.reader.xml.XMLStatementReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

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

    private TestUtil testUtil;

    @BeforeEach
    void setUp() {
        testUtil = new TestUtil();
    }

    @Test
    public void testReadCsv() throws IOException {
        MockMultipartFile file = new MockMultipartFile(
                "monthlyReportFile",
                "statements.csv",
                "text/plain",
                testUtil.readFileFromResources("bank-statement-controller/single-valid-statement.csv").getBytes()
        );

        statementFileReader.readFileContent(file);

        // Verify that the CSVStatementReader's read method was called once
        verify(csvStatementReader, times(1)).read(anyString());

        // Verify that the XMLStatementReader's read method was not called
        verify(xmlStatementReader, times(0)).read(anyString());
    }

    @Test
    public void testReadXml() throws IOException {
        MockMultipartFile file = new MockMultipartFile(
                "monthlyReportFile",
                "statements.xml",
                "text/plain",
                testUtil.readFileFromResources("bank-statement-controller/single-valid-statement.xml").getBytes()
        );

        statementFileReader.readFileContent(file);

        // Verify that the XMLStatementReader's read method was called once
        verify(xmlStatementReader, times(1)).read(anyString());

        // Verify that the CSVStatementReader's read method was not called
        verify(csvStatementReader, times(0)).read(anyString());
    }

}