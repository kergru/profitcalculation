package com.kgr.profitcalculation.console.cli;

public class ParseInputException extends RuntimeException {

    ParseInputException(String requiredFormat) {
        super("Invalid input, required format: " + requiredFormat);
    }
}
