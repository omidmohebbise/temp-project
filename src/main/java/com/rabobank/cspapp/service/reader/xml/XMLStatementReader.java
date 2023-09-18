package com.rabobank.cspapp.service.reader.xml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.rabobank.cspapp.model.Statement;
import com.rabobank.cspapp.service.reader.StatementReader;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XMLStatementReader implements StatementReader {

    /**
     * Reads and parses XML-formatted statements from the provided XML string content.
     *
     * @param statements The XML content representing the statements to be read and parsed.
     * @return A list of {@link Statement} objects parsed from the XML content.
     * @throws IllegalArgumentException If an error occurs during XML parsing or if the XML data
     *                                  is not well-formed or does not adhere to the expected structure.
     */
    @Override
    public List<Statement> read(String statements) {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            return xmlMapper.readValue(statements, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error encountered while parsing the XML data. Please ensure the XML" +
                    " is well-formed and adheres to the expected structure.", e);
        }
    }
}
