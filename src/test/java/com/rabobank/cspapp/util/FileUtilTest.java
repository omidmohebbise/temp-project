package com.rabobank.cspapp.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileUtilTest {

    private FileUtil fileUtil;

    @BeforeEach
    void setUp() {
        fileUtil = new FileUtil();
    }

    @Test
    public void testGetFileExtensionWithValidFileName() {
        String fileName = "example.csv";
        String extension = fileUtil.getFileExtension(fileName);
        assertEquals(".csv", extension);
    }
    @Test
    public void testGetFileExtensionWithNoExtension() {
        String fileName = "fileWithoutExtension";
        String extension = fileUtil.getFileExtension(fileName);
        assertEquals("", extension);
    }

    @Test
    public void testFileToString() {
        String content = "Hello, World!";
        MultipartFile multipartFile = new MockMultipartFile("test.txt", content.getBytes());

        String result = fileUtil.fileToString(multipartFile);

        assertEquals(content, result);
    }

    @Test
    public void testFileToStringWithIOException() throws IOException {
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(multipartFile.getBytes()).thenThrow(new IOException("Simulated IOException"));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> fileUtil.fileToString(multipartFile)
        );

        assertEquals("Error in reading file content! Please try again", exception.getMessage());
    }
}