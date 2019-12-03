package com.kgr.profitcalculation.rest;

import com.kgr.profitcalculation.domain.Expense;
import com.kgr.profitcalculation.domain.MonthlyWork;
import com.kgr.profitcalculation.domain.ProfitCalculationCommand;
import com.kgr.profitcalculation.domain.tax.TaxPrePayment;
import com.kgr.profitcalculation.rest.request.ExpenseRequest;
import com.kgr.profitcalculation.rest.request.MonthlyWorkRequest;
import com.kgr.profitcalculation.rest.request.ProfitCalculationRequest;
import com.kgr.profitcalculation.rest.request.TaxPrePaymentRequest;

import java.time.Month;
import java.util.Collection;
import java.util.List;

import static com.kgr.profitcalculation.domain.ProfitCalculator.money;
import static java.util.stream.Collectors.toList;

class ProfitCalculationRequestMapper {

    ProfitCalculationCommand toCommand(int year, ProfitCalculationRequest calculationRequest) {
        TaxPrePayment businessTaxPrePayment = createTaxPrePayment(calculationRequest.getBusinessTaxPrePayment());
        TaxPrePayment incomeTaxPrePayment = createTaxPrePayment(calculationRequest.getIncomeTaxPrePayment());
        TaxPrePayment solidarityTaxPrePayment = createTaxPrePayment(calculationRequest.getSolidarityTaxPrePayment());
        List<MonthlyWork> monthlyWorks = createMonthlyWorks(calculationRequest.getMonthlyWorks());
        List<Expense> fixedExpenses = createFixedExpenses(calculationRequest.getFixedExpenses());

        return new ProfitCalculationCommand(businessTaxPrePayment, incomeTaxPrePayment, solidarityTaxPrePayment, monthlyWorks, fixedExpenses);
    }

    private List<Expense> createFixedExpenses(List<ExpenseRequest> fixedExpenses) {
        List<Expense> retVal = null;
        if (fixedExpenses != null) {
            retVal = fixedExpenses.stream().map(e -> createFixedExpense(e)).collect(toList());
        }
        return retVal;
    }

    private Expense createFixedExpense(ExpenseRequest expenseRequest) {
        return new Expense(expenseRequest.getCategory(), expenseRequest.getTitle(), money(expenseRequest.getAmount()),
                (expenseRequest.getStartMonth() != null ? Month.of(expenseRequest.getStartMonth()) : null),
                (expenseRequest.getEndMonth() != null ? Month.of(expenseRequest.getEndMonth()) : null));
    }

    private List<MonthlyWork> createMonthlyWorks(Collection<MonthlyWorkRequest> monthlyWorks) {
        List<MonthlyWork> retVal = null;
        if (monthlyWorks != null) {
            retVal = monthlyWorks.stream().map(w -> createMonthlyWork(w)).collect(toList());
        }
        return retVal;
    }

    private MonthlyWork createMonthlyWork(MonthlyWorkRequest monthlyWorkRequest) {
        return new MonthlyWork(monthlyWorkRequest.getMonth(), money(monthlyWorkRequest.getRatePerHour()), monthlyWorkRequest.getHoursWorked());
    }

    private TaxPrePayment createTaxPrePayment(TaxPrePaymentRequest taxPrePaymentRequest) {
        TaxPrePayment retVal = null;
        if (taxPrePaymentRequest != null) {
            retVal = new TaxPrePayment(money(taxPrePaymentRequest.getAmount()), taxPrePaymentRequest.getInterval());
        }
        return retVal;
    }
}
