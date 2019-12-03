package com.kgr.profitcalculation.domain;

import com.kgr.profitcalculation.domain.tax.TaxPrePayment;

import javax.money.MonetaryAmount;

import static com.kgr.profitcalculation.domain.ProfitCalculator.ZERO;
import static java.util.Arrays.stream;
import static org.javamoney.moneta.function.MonetaryFunctions.sum;

class YearlyProfitCalculator extends AbstractCalculator<YearlyProfitCalculation> {

    YearlyProfitCalculator(YearlyProfitCalculation yearlyCalculation) {
        super(yearlyCalculation);
    }

    void calculate(ProfitCalculationCommand calculationParameter) {
        setTaxPrePayments(calculationParameter);
        calculateMonths(calculationParameter);
        calculateTotals(calculationParameter);
    }

    @Override
    protected MonetaryAmount getBusinessTaxPrePaymentAmount(ProfitCalculationCommand calculationParameter) {
        return getTaxPrePaymentAmount(calculationParameter.getBusinessTaxPrePayment());
    }

    @Override
    protected MonetaryAmount getIncomeTaxPrePaymentAmount(ProfitCalculationCommand calculationParameter) {
        return getTaxPrePaymentAmount(calculationParameter.getIncomeTaxPrePayment());
    }

    @Override
    protected MonetaryAmount getSolidarityTaxPrePaymentAmount(ProfitCalculationCommand calculationParameter) {
        return getTaxPrePaymentAmount(calculationParameter.getSolidarityTaxPrePayment());
    }

    private MonetaryAmount getTaxPrePaymentAmount(TaxPrePayment taxPrePayment) {
        return taxPrePayment != null ? taxPrePayment.getMonthlyTaxPrePaymentAmount().multiply(12) : ZERO;
    }

    private void calculateTotals(ProfitCalculationCommand calculationParameter) {
        MonthlyProfitCalculation[] monthlyProfitCalculations = this.calculation.getMonthlyProfitCalculations();

        double hoursWorked = calculateTotalHoursWorked(monthlyProfitCalculations);
        this.calculation.setHoursWorked(hoursWorked);

        MonetaryAmount totalGrossPay = totalGrossPay(monthlyProfitCalculations);

        calculate(calculationParameter, totalGrossPay);
    }

    private double calculateTotalHoursWorked(MonthlyProfitCalculation[] monthlyProfitCalculations) {
        return stream(monthlyProfitCalculations).mapToDouble(c -> c.getHoursWorked()).sum();
    }

    private MonetaryAmount totalGrossPay(MonthlyProfitCalculation[] monthlyProfitCalculations) {
        return stream(monthlyProfitCalculations).map(c -> c.getGrossPay()).reduce(sum()).orElse(ZERO);
    }

    private void calculateMonths(ProfitCalculationCommand calculationParameter) {
        for (MonthlyProfitCalculation monthlyCalc : this.calculation.getMonthlyProfitCalculations()) {
            new MonthlyProfitCalculator(monthlyCalc).calculate(calculationParameter);
        }
    }

    private void setTaxPrePayments(ProfitCalculationCommand calculationParameter) {
        this.calculation.setBusinessTaxPrePayment(calculationParameter.getBusinessTaxPrePayment());
        this.calculation.setIncomeTaxPrePayment(calculationParameter.getIncomeTaxPrePayment());
        this.calculation.setSolidarityTaxPrePayment(calculationParameter.getSolidarityTaxPrePayment());
    }
}
