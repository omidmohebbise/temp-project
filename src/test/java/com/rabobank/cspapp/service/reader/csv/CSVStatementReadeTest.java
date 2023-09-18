package com.rabobank.cspapp.service.reader.csv;

import com.rabobank.cspapp.model.Statement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVStatementReadeTest {
    private final static String VALID_STATEMENT_CSV = """
            Reference,Account Number,Description,Start Balance,Mutation,End Balance
            1,Account1,Description1,100.0,10.0,110.0
            2,Account2,Description2,200.0,-20.0,180.0
            3,Account3,Description3,300.0,30.0,330.0""";


    private final static String INVALID_STATEMENT_CSV= """
                Reference,Account Number,Description,Start Balance,Mutation,End Balance
                1,Account1,Description1,invalidNumber,10.0,110.0
                """;
    CSVStatementReader csvStatementReader;


    @BeforeEach
    void setUp() {
        csvStatementReader = new CSVStatementReader();
    }


    @Test
    public void assertSizeOfStatementsInLoading() {
        // Assert that the size of the parsed statements list matches the expected size
        List<Statement> statements = csvStatementReader.read(VALID_STATEMENT_CSV);
        assertEquals(3, statements.size());
    }

    @Test
    public void checkStatementGetLoadedProperly() {
        // Assert the values of the first and last statements
        List<Statement> statements = csvStatementReader.read(VALID_STATEMENT_CSV);
        Statement statement1 = statements.get(0);
        assertEquals("1", statement1.reference());
        assertEquals("Account1", statement1.accountNumber());
        assertEquals("Description1", statement1.description());
        assertEquals(100.0, statement1.startBalance().doubleValue(), 0.01);
        assertEquals(10.0, statement1.mutation().doubleValue(), 0.01);
        assertEquals(110.0, statement1.endBalance().doubleValue(), 0.01);

        Statement lastStatement = statements.get(2);
        assertEquals("3", lastStatement.reference());
        assertEquals("Account3", lastStatement.accountNumber());
        assertEquals("Description3", lastStatement.description());
        assertEquals(300.0, lastStatement.startBalance().doubleValue(), 0.01);
        assertEquals(30.0, lastStatement.mutation().doubleValue(), 0.01);
        assertEquals(330.0, lastStatement.endBalance().doubleValue(), 0.01);

    }

    @Test
    void checkValidExceptionWhenFileContentIsNotValid() {
        assertThrows(RuntimeException.class, () ->
                csvStatementReader.read(INVALID_STATEMENT_CSV)
        );
    }
}