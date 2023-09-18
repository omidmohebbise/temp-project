package com.rabobank.cspapp.service.validator.vm;

import com.rabobank.cspapp.model.Statement;

import java.util.List;

public record InvalidStatementVM(List<Statement> duplicatedStatement, List<Statement> invalidEndBalance) {
}
