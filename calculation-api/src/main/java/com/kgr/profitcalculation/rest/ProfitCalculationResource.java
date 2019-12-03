package com.kgr.profitcalculation.rest;

import com.kgr.profitcalculation.domain.ProfitCalculationCommand;
import com.kgr.profitcalculation.domain.ProfitCalculator;
import com.kgr.profitcalculation.domain.YearlyProfitCalculation;
import com.kgr.profitcalculation.rest.request.ProfitCalculationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequiredArgsConstructor
public class ProfitCalculationResource {

    private final ProfitCalculator profitCalculator;

    private ProfitCalculationRequestMapper profitCalculationRequestMapper = new ProfitCalculationRequestMapper();

    @GetMapping(
            value = "/api/calculation/{year}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<YearlyProfitCalculation> get(@PathVariable("year") int year) {
        YearlyProfitCalculation calculation = profitCalculator.getCalculation(year);
        return ResponseEntity.ok(calculation);
    }

    @PostMapping(
            value = "/api/calculation/{year}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<YearlyProfitCalculation> calculate(@PathVariable("year") int year, @RequestBody @Valid ProfitCalculationRequest calculationRequest) {
        ProfitCalculationCommand command = profitCalculationRequestMapper.toCommand(year, calculationRequest);
        YearlyProfitCalculation calculation = profitCalculator.calculate(year, command);
        return ResponseEntity.ok(calculation);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new TreeMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
