package com.rabobank.cspapp.service.validator;

import com.rabobank.cspapp.model.Statement;
import com.rabobank.cspapp.service.validator.vm.InvalidStatementVM;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class StatementValidator {

    /**
     * Validates a list of statements and produces a {@link InvalidStatementVM} containing lists
     * of duplicate and invalid statements.
     *
     * @param statements The list of statements to be validated.
     * @return A {@link InvalidStatementVM} object with lists of duplicate and invalid statements.
     */
    public InvalidStatementVM validateStatements(List<Statement> statements) {
        var duplicateStatements = validateDuplicateStatements(statements);
        var invalidStatements = validateInvalidEndBalanceStatements(statements);
        return new InvalidStatementVM(duplicateStatements, invalidStatements);
    }

    /**
     * Validates the end balance of a single statement by comparing it to the calculated
     * end balance based on the start balance and mutation.
     *
     * @param statement The statement to be validated.
     * @return {@code true} if the end balance is valid, {@code false} otherwise.
     */
    protected boolean isValidEndBalance(Statement statement) {
        BigDecimal calculatedEndBalance = statement.startBalance().add(statement.mutation());
        return statement.endBalance().compareTo(calculatedEndBalance) == 0;
    }

    /**
     * Filters and returns a list of statements with invalid end balances from the given list.
     *
     * @param statements The list of statements to be validated.
     * @return A list of statements with invalid end balances.
     */
    protected List<Statement> validateInvalidEndBalanceStatements(List<Statement> statements) {
        return statements.stream().filter(statement -> !isValidEndBalance(statement)).toList();
    }

    /**
     * Identifies and returns a list of duplicate statements from the given list.
     *
     * @param statements The list of statements to be validated.
     * @return A list of duplicate statements.
     */
    protected List<Statement> validateDuplicateStatements(List<Statement> statements) {
        List<Statement> repeatedStatements = new ArrayList<>();
        Set<String> references = new HashSet<>();
        for (Statement statement : statements) {
            if (!references.add(statement.reference())) {
                repeatedStatements.add(statement);
            }
        }
        return repeatedStatements;
    }

}