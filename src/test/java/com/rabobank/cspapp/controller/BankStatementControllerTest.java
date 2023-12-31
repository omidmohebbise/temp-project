package com.rabobank.cspapp.controller;

import com.rabobank.cspapp.service.reader.StatementFileReader;
import com.rabobank.cspapp.service.validator.StatementValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.multipart.MultipartFile;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BankStatementControllerTest {
    @InjectMocks
    BankStatementController bankStatementController;

    @Mock
    StatementValidator statementValidator;
    @Mock
    StatementFileReader statementFileReader;

    @BeforeEach
    void setUp() {
        bankStatementController = new BankStatementController(statementValidator, statementFileReader);
    }

    @Test
    void checkEmptyFileException() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);
        var result  = assertThrows(IllegalArgumentException.class, ()-> bankStatementController.validateMonthlyStatements(file));
        assertEquals(result.getMessage(), "The file is empty.");

    }
}