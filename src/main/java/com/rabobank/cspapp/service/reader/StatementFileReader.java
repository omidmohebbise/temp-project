package com.rabobank.cspapp.service.reader;

import com.rabobank.cspapp.model.Statement;
import com.rabobank.cspapp.service.reader.csv.CSVStatementReader;
import com.rabobank.cspapp.service.reader.xml.XMLStatementReader;
import com.rabobank.cspapp.service.validator.ValidFileFormat;
import com.rabobank.cspapp.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
     * Reads and processes the content of a monthly report file uploaded as a {@link MultipartFile}.
     * The method extracts the content from the file, determines its format, and parses it into
     * a list of {@link Statement} objects.
     *
     * @param monthlyReportFile The {@link MultipartFile} containing the monthly report file to be read.
     * @return A list of {@link Statement} objects parsed from the contents of the uploaded file.
     * @throws IllegalArgumentException If an error occurs during file processing or if the file format
     *                                  is not supported or recognized.
     */
    public List<Statement> readFileContent(MultipartFile monthlyReportFile) {
        FileUtil fileUtil = new FileUtil();
        String fileContent = fileUtil.fileToString(monthlyReportFile);

        ValidFileFormat validFileFormat = ValidFileFormat.getByValue(fileUtil.getFileExtension(
                Objects.requireNonNull(monthlyReportFile.getOriginalFilename())));

        List<Statement> statements;
        if (Objects.equals(validFileFormat, ValidFileFormat.XML)) {
            statements = xmlStatementReader.read(fileContent);
        } else {
            statements = csvStatementReader.read(fileContent);
        }
        return statements;
    }

}
