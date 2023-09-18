package com.rabobank.cspapp.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileUtil {
    public String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf(".") < 0)
            return "";
        else
            return fileName.substring(fileName.lastIndexOf("."));
    }

    public String fileToString(MultipartFile file) {
        byte[] fileBytes;
        try {
            fileBytes = file.getBytes();
        } catch (IOException e) {
            throw new IllegalArgumentException("Error in reading file content! Please try again", e);
        }
        return new String(fileBytes, StandardCharsets.UTF_8);
    }
}
