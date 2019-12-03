package com.kgr.profitcalculation.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kgr.profitcalculation.domain.Expense;
import com.kgr.profitcalculation.domain.MonthlyWork;
import com.kgr.profitcalculation.domain.ProfitCalculationCommand;
import com.kgr.profitcalculation.domain.tax.TaxPrePayment;
import com.kgr.profitcalculation.rest.request.ExpenseRequest;
import com.kgr.profitcalculation.rest.request.MonthlyWorkRequest;
import com.kgr.profitcalculation.rest.request.ProfitCalculationRequest;
import com.kgr.profitcalculation.rest.request.TaxPrePaymentRequest;

import java.util.Arrays;

import static com.kgr.profitcalculation.domain.ExpenseCategory.BUSINESS;
import static com.kgr.profitcalculation.domain.ExpenseCategory.PRIVATE;
import static com.kgr.profitcalculation.domain.ProfitCalculator.money;
import static com.kgr.profitcalculation.domain.tax.TaxPrePaymentInterval.MONTHLY;
import static com.kgr.profitcalculation.domain.tax.TaxPrePaymentInterval.QUARTERLY;
import static com.kgr.profitcalculation.domain.tax.TaxPrePaymentInterval.YEARLY;
import static java.time.Month.AUGUST;
import static java.time.Month.FEBRUARY;
import static java.time.Month.NOVEMBER;

class TestDataFactory {

    static String aProfitCalculationRequest() throws JsonProcessingException {
        ProfitCalculationRequest request = new ProfitCalculationRequest();
        request.setBusinessTaxPrePayment(new TaxPrePaymentRequest(10d, MONTHLY));
        request.setIncomeTaxPrePayment(new TaxPrePaymentRequest(60d, QUARTERLY));
        request.setSolidarityTaxPrePayment(new TaxPrePaymentRequest(24d, YEARLY));
        request.setFixedExpenses(Arrays.asList(
                new ExpenseRequest(BUSINESS, "xy", 30d, 2, 11),
                new ExpenseRequest(PRIVATE, "ab", 20d, null, null)));
        request.setMonthlyWorks(Arrays.asList(
                new MonthlyWorkRequest(FEBRUARY, 80d, 40d),
                new MonthlyWorkRequest(AUGUST, 80d, 50d)
        ));
        String retVal = new ObjectMapper().writeValueAsString(request);
        return retVal;
    }

    static ProfitCalculationCommand aProfitCalculationCommand() {
        return new ProfitCalculationCommand(
                new TaxPrePayment(money(10), MONTHLY),
                new TaxPrePayment(money(60), QUARTERLY),
                new TaxPrePayment(money(24), YEARLY),
                Arrays.asList(
                        new MonthlyWork(FEBRUARY, money(80), 40d),
                        new MonthlyWork(AUGUST, money(80), 50d)),
                Arrays.asList(
                        new Expense(BUSINESS, "xy", money(30), FEBRUARY, NOVEMBER),
                        new Expense(PRIVATE, "ab", money(20), null, null)
                )
        );
    }
}
