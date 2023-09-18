package com.rabobank.cspapp;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TestUtil {
    public String readFileFromResources(String fileName) throws IOException {
        return StreamUtils.copyToString(new ClassPathResource(fileName).getInputStream(), StandardCharsets.UTF_8);
    }
}
