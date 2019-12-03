package com.kgr.profitcalculation.domain;

import com.kgr.profitcalculation.domain.tax.TaxPrePayment;
import lombok.Value;

import java.time.Month;
import java.util.Collection;
import java.util.List;

import static com.kgr.profitcalculation.domain.MonthlyWork.noProject;

@Value
public class ProfitCalculationCommand {

    private final TaxPrePayment businessTaxPrePayment;

    private final TaxPrePayment incomeTaxPrePayment;

    private final TaxPrePayment solidarityTaxPrePayment;

    private final Collection<MonthlyWork> monthlyWorks;

    private final List<Expense> fixedExpenses;

    MonthlyWork getMonthlyWork(Month month) {
        if (monthlyWorks == null) {
            return noProject(month);
        }
        return monthlyWorks.stream().filter(mw -> mw.getMonth() == month).findFirst().orElse(noProject(month));
    }

    public void validate() {
        if (businessTaxPrePayment != null) {
            businessTaxPrePayment.validate();
        }

        if (incomeTaxPrePayment != null) {
            incomeTaxPrePayment.validate();
        }

        if (solidarityTaxPrePayment != null) {
            solidarityTaxPrePayment.validate();
        }

        if (monthlyWorks != null) {
            monthlyWorks.forEach((work) -> work.validate());
        }

        if (fixedExpenses != null) {
            fixedExpenses.forEach((fixedExpense) -> fixedExpense.validate());
        }
    }
}
