package com.kgr.profitcalculation.console.adapter;

import com.kgr.profitcalculation.console.cli.ProfitCalculationInput;
import com.kgr.profitcalculation.domain.ProfitCalculationCommand;
import com.kgr.profitcalculation.domain.ProfitCalculator;
import com.kgr.profitcalculation.domain.YearlyProfitCalculation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProfitCalculationConsoleAdapter {

    private final ProfitCalculator profitCalculator;

    public YearlyProfitCalculation getCalculation(int year) {
        return profitCalculator.getCalculation(year);
    }

    public YearlyProfitCalculation calculate(ProfitCalculationInput calculationInput) {
        return profitCalculator.calculate(
                calculationInput.getYear().getValue(), createCalculationParameter(calculationInput));
    }

    private ProfitCalculationCommand createCalculationParameter(ProfitCalculationInput calculationInput) {
        return new ProfitCalculationCommand(
                calculationInput.getBusinessTaxPrePayment(),
                calculationInput.getIncomeTaxPrePayment(),
                calculationInput.getSolidarityTaxPrePayment(),
                calculationInput.getMonthlyWorks().values(),
                calculationInput.getExpenses());
    }
}