package com.rabobank.cspapp.controller.mapper;

import com.rabobank.cspapp.controller.dto.StatementReport;
import com.rabobank.cspapp.model.Statement;
import com.rabobank.cspapp.service.validator.vm.InvalidStatementVM;

import java.util.ArrayList;
import java.util.List;

public class StatementMapper {
    public static List<StatementReport> mapToResponseDTO(InvalidStatementVM invalidStatementVM) {
        List<StatementReport> list = new ArrayList<>();
        for (Statement statement : invalidStatementVM.duplicatedStatement()) {
            list.add(new StatementReport(statement.reference(),
                    String.format("The transaction %s failed due to duplication.", statement.reference())));
        }
        for (Statement statement : invalidStatementVM.invalidEndBalance()) {
            list.add(new StatementReport(statement.reference(),
                    String.format("The transaction %s failed: The end balance is not as expected after the mutation.",
                            statement.reference())));
        }
        return list;

    }
}
