package com.rabobank.cspapp.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.math.BigDecimal;


public record Statement(
        String reference,
        @JsonProperty( "Account Number")
        @JacksonXmlProperty( localName = "accountNumber")
        String accountNumber,
        String description,
        @JsonProperty( "Start Balance")
        @JacksonXmlProperty( localName = "startBalance")
        BigDecimal startBalance,
        BigDecimal mutation,
        @JsonProperty( "End Balance")
        @JacksonXmlProperty( localName = "endBalance")
        BigDecimal endBalance) {

}
