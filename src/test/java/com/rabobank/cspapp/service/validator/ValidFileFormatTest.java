package com.rabobank.cspapp.service.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidFileFormatTest {
    @Test
    public void testGetByValueWithValidValue() {
        ValidFileFormat xmlFormat = ValidFileFormat.getByValue(".xml");
        ValidFileFormat csvFormat = ValidFileFormat.getByValue(".csv");

        assertEquals(ValidFileFormat.XML, xmlFormat);
        assertEquals(ValidFileFormat.CSV, csvFormat);
    }

    @Test
    public void testGetByValueWithInvalidValue() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> ValidFileFormat.getByValue(".doc")
        );

        assertEquals(
                "Invalid file type. Please upload a valid XML or CSV bank statement.",
                exception.getMessage()
        );
    }
}