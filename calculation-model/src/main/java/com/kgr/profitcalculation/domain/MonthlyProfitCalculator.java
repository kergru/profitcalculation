package com.kgr.profitcalculation.domain;

import javax.money.MonetaryAmount;
import java.time.Month;
import java.time.YearMonth;
import java.util.List;

import static com.kgr.profitcalculation.domain.FixedMonthlyExpense.from;
import static com.kgr.profitcalculation.domain.ProfitCalculator.ZERO;
import static java.time.Month.DECEMBER;
import static java.time.Month.JANUARY;
import static java.util.stream.Collectors.toList;

class MonthlyProfitCalculator extends AbstractCalculator<MonthlyProfitCalculation> {

    MonthlyProfitCalculator(MonthlyProfitCalculation monthlyProfitCalculation) {
        super(monthlyProfitCalculation);
    }

    void calculate(ProfitCalculationCommand calculationParameter) {
        assert (calculationParameter != null);

        MonthlyWork monthlyWork = calculationParameter.getMonthlyWork(calculation.getYearMonth().getMonth());

        calculation.setRatePerHour(monthlyWork.getRatePerHour());

        double hoursWorked = monthlyWork.getHoursWorked();
        calculation.setHoursWorked(hoursWorked);

        MonetaryAmount ratePerHour = monthlyWork.getRatePerHour() != null ? monthlyWork.getRatePerHour() : ZERO;
        MonetaryAmount grossPay = ratePerHour.multiply(hoursWorked);
        calculation.setGrossPay(grossPay);

        setFixedExpenses(calculation, calculationParameter.getFixedExpenses());

        calculate(calculationParameter, grossPay);
    }

    private void setFixedExpenses(MonthlyProfitCalculation calculation, List<Expense> fixedExpenses) {
        if (fixedExpenses != null) {
            List<Expense> actualExpenses = fixedExpenses.stream().filter(e -> isActualExpense(calculation.getYearMonth(), e)).collect(toList());
            actualExpenses.stream().forEach(expense -> {
                FixedMonthlyExpense fixedExpense = from(expense);
                if (expense.isBusinessExpense()) {
                    calculation.getFixedBusinessExpenses().add(fixedExpense);
                } else {
                    calculation.getFixedPrivateExpenses().add(fixedExpense);
                }
            });
        }
    }

    private boolean isActualExpense(YearMonth yearMonth, Expense expense) {
        Month current = yearMonth.getMonth();
        Month start = expense.getStartMonth() != null ? expense.getStartMonth() : JANUARY;
        Month end = expense.getEndMonth() != null ? expense.getEndMonth() : DECEMBER;
        return start.getValue() <= current.getValue() && current.getValue() <= end.getValue();
    }

    @Override
    protected MonetaryAmount getBusinessTaxPrePaymentAmount(ProfitCalculationCommand calculationParameter) {
        MonetaryAmount amount = ZERO;
        if (calculationParameter.getBusinessTaxPrePayment() != null) {
            amount = calculationParameter.getBusinessTaxPrePayment().getMonthlyTaxPrePaymentAmount();
        }
        return amount;
    }

    @Override
    protected MonetaryAmount getIncomeTaxPrePaymentAmount(ProfitCalculationCommand calculationParameter) {
        MonetaryAmount amount = ZERO;
        if (calculationParameter.getIncomeTaxPrePayment() != null) {
            amount = calculationParameter.getIncomeTaxPrePayment().getMonthlyTaxPrePaymentAmount();
        }
        return amount;
    }

    @Override
    protected MonetaryAmount getSolidarityTaxPrePaymentAmount(ProfitCalculationCommand calculationParameter) {
        MonetaryAmount amount = ZERO;
        if (calculationParameter.getSolidarityTaxPrePayment() != null) {
            amount = calculationParameter.getSolidarityTaxPrePayment().getMonthlyTaxPrePaymentAmount();
        }
        return amount;
    }
}
