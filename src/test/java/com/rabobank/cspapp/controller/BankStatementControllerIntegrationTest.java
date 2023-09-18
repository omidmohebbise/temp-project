package com.rabobank.cspapp.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabobank.cspapp.controller.dto.StatementReport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BankStatementControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void validateMonthlyStatements_SingleValidXML() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "monthlyReportFile",
                "statements.xml",
                "text/plain",
                readFileFromResources("bank-statement-controller/single-valid-statement.xml").getBytes()
        );

        var result = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/bank-statements/monthly-validator")
                        .file(file))
                .andExpect(status().isOk())
                .andReturn();
        List<StatementReport> validationResponse = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });
        assertTrue(validationResponse.isEmpty());

    }

    @Test
    void validateMonthlyStatements_InvalidSingleStatementXML() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "monthlyReportFile",
                "statements.xml",
                "text/plain",
                readFileFromResources("bank-statement-controller/single-invalid-statement.xml").getBytes()
        );

        var result = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/bank-statements/monthly-validator")
                        .file(file))
                .andExpect(status().isOk())
                .andReturn();
        List<StatementReport> validationResponse = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        assertFalse(validationResponse.isEmpty());
        Assertions.assertEquals(validationResponse.get(0).reference(), "138932");
    }

    @Test
    void validateMonthlyStatements_RepetitiveInValidXML() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "monthlyReportFile",
                "statements.xml",
                "text/plain",
                readFileFromResources("bank-statement-controller/invalid-repetitive-statement.xml").getBytes()
        );

        var result = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/bank-statements/monthly-validator")
                        .file(file))
                .andExpect(status().isOk())
                .andReturn();
        List<StatementReport> validationResponse = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });
        assertFalse(validationResponse.isEmpty());

        Assertions.assertEquals(validationResponse.get(0).reference(), "138932");
    }


    @Test
    void validateMonthlyStatements_ValidSingleValidCSV() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "monthlyReportFile",
                "statements.csv",
                "text/plain",
                readFileFromResources("bank-statement-controller/single-valid-statement.csv").getBytes()
        );

        var result = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/bank-statements/monthly-validator")
                        .file(file))
                .andExpect(status().isOk())
                .andReturn();
        List<StatementReport>  validationResponse = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });
        assertTrue(validationResponse.isEmpty());
    }

    @Test
    void validateMonthlyStatements_InvalidSingleStatementCSV() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "monthlyReportFile",
                "statements.csv",
                "text/plain",
                readFileFromResources("bank-statement-controller/single-invalid-statement.csv").getBytes()
        );

        var result = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/bank-statements/monthly-validator")
                        .file(file))
                .andExpect(status().isOk())
                .andReturn();
        List<StatementReport>   validationResponse = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });
        assertFalse(validationResponse.isEmpty());
        Assertions.assertEquals(validationResponse.get(0).reference(), "183398");
    }

    @Test
    void validateMonthlyStatements_RepetitiveValidCSV() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "monthlyReportFile",
                "statements.csv",
                "text/plain",
                readFileFromResources("bank-statement-controller/invalid-repetitive-statement.csv").getBytes()
        );

        var result = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/bank-statements/monthly-validator")
                        .file(file))
                .andExpect(status().isOk())
                .andReturn();
        List<StatementReport> validationResponse = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });
        assertFalse(validationResponse.isEmpty());
        Assertions.assertEquals(validationResponse.get(0).reference(), "183398");
    }


    private String readFileFromResources(String fileName) throws IOException {
        return StreamUtils.copyToString(new ClassPathResource(fileName).getInputStream(), StandardCharsets.UTF_8);
    }
}
