package com.rabobank.cspapp.service.validator;



public enum ValidFileFormat {
    XML(".xml"), CSV(".csv");

    private final String value;
    ValidFileFormat(String s) {
        this.value =  s;
    }

    public static ValidFileFormat getByValue(String value) {
        for (ValidFileFormat format : ValidFileFormat.values()) {
            if (format.getValue().equalsIgnoreCase(value)) {
                return format;
            }
        }
        throw new IllegalArgumentException("Invalid file type. Please upload a valid XML or CSV bank statement.");
    }

    public String getValue() {
        return value;
    }
}
