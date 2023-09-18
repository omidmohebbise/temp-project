package com.rabobank.cspapp.service.validator;

import com.rabobank.cspapp.model.Statement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class StatementValidatorTest {

    @InjectMocks
    private StatementValidator statementValidator;

    @Test
    public void test_isValidEndBalance() {
        Statement statement1 = new Statement("", "", "", BigDecimal.valueOf(10.25),
                BigDecimal.valueOf(-0.25), BigDecimal.valueOf(10.0));
        assertTrue(statementValidator.isValidEndBalance(statement1));

        Statement statement2 = new Statement("", "", "", BigDecimal.valueOf(10.25),
                BigDecimal.valueOf(-0.25), BigDecimal.valueOf(10.25));
        assertFalse(statementValidator.isValidEndBalance(statement2));
    }

    @Test
    void test_validateDuplicateStatements() {
        List<Statement> list = new ArrayList<>();
        list.add(new Statement("XXX","","",
                null,null,null));
        list.add(new Statement("XXX","","",
                null,null,null));
        assertFalse(statementValidator.validateDuplicateStatements(list).isEmpty());

        list.clear();
        list.add(new Statement("XXX","","",
                null,null,null));
        list.add(new Statement("YYY","","",
                null,null,null));
        assertTrue(statementValidator.validateDuplicateStatements(list).isEmpty());

    }
}