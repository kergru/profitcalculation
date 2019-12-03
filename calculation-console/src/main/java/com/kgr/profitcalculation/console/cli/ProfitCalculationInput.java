package com.kgr.profitcalculation.console.cli;

import com.kgr.profitcalculation.domain.Expense;
import com.kgr.profitcalculation.domain.MonthlyWork;
import com.kgr.profitcalculation.domain.tax.TaxPrePayment;
import lombok.Getter;
import lombok.Setter;

import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ProfitCalculationInput {

    private Year year;

    private TaxPrePayment businessTaxPrePayment;

    private TaxPrePayment incomeTaxPrePayment;

    private TaxPrePayment solidarityTaxPrePayment;

    private Map<Month, MonthlyWork> monthlyWorks = new HashMap<>();

    private List<Expense> expenses = new ArrayList<>();

    public void addMonthlyWork(MonthlyWork monthlyWork) {
        monthlyWorks.put(monthlyWork.getMonth(), monthlyWork);
    }

    public boolean hasMonthlyWork(Month month) {
        return monthlyWorks.containsKey(month);
    }

    public void addExpense(Expense monthlyExpense) {
        expenses.add(monthlyExpense);
    }
}
