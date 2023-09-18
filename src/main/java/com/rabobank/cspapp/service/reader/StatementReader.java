package com.rabobank.cspapp.service.reader;

import com.rabobank.cspapp.model.Statement;

import java.util.List;

public interface StatementReader {
    List<Statement> read(String statements);
}
