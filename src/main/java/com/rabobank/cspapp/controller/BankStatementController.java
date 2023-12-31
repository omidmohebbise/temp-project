package com.rabobank.cspapp.controller;

import com.rabobank.cspapp.controller.dto.ExceptionResponse;
import com.rabobank.cspapp.controller.dto.StatementReport;
import com.rabobank.cspapp.controller.mapper.StatementMapper;
import com.rabobank.cspapp.service.reader.StatementFileReader;
import com.rabobank.cspapp.service.validator.StatementValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.util.concurrent.TimeoutException;

@RestController
@CrossOrigin
@RequestMapping("/api/bank-statements")
public class BankStatementController extends ResponseEntityExceptionHandler {
    private final StatementValidator statementValidator;
    private final StatementFileReader statementFileReader;

    public BankStatementController(StatementValidator statementValidator, StatementFileReader statementFileReader) {
        this.statementValidator = statementValidator;
        this.statementFileReader = statementFileReader;
    }

    @Operation(summary = "Validate Monthly Statements")
    @ApiResponse(responseCode = "200", description = "Validation result", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = StatementReport.class))
    })
    @ApiResponse(responseCode = "400", description = "Bad request")
    @PostMapping(value = "/monthly-validator", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> validateMonthlyStatements(@RequestParam(value = "monthlyReportFile") MultipartFile monthlyReportFile) {
        if (monthlyReportFile.isEmpty()) {
            throw new IllegalArgumentException("The file is empty.");
        } else {
            return ResponseEntity.ok(StatementMapper.mapToResponseDTO(
                    statementValidator.validateStatements(
                            statementFileReader.readFileContent(monthlyReportFile)
                    )));
        }
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ExceptionResponse> handleRunTimeExceptions(RuntimeException e) {
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON)
                .body(new ExceptionResponse("Oops! Something is wrong. Please check your file content to be correct" +
                        " and try again."));
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentExceptions(IllegalArgumentException e) {
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON)
                .body(new ExceptionResponse(e.getMessage()));
    }

    @ExceptionHandler({TimeoutException.class})
    public ResponseEntity<ExceptionResponse> timeout() {
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).
                body(new ExceptionResponse("Please try again."));
    }

}
