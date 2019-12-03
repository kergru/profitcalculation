package com.kgr.profitcalculation.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kgr.profitcalculation.rest.request.ExpenseRequest;
import com.kgr.profitcalculation.rest.request.MonthlyWorkRequest;
import com.kgr.profitcalculation.rest.request.ProfitCalculationRequest;
import com.kgr.profitcalculation.rest.request.TaxPrePaymentRequest;

import java.util.Arrays;

import static com.kgr.profitcalculation.domain.ExpenseCategory.BUSINESS;
import static com.kgr.profitcalculation.domain.ExpenseCategory.PRIVATE;
import static com.kgr.profitcalculation.domain.tax.TaxPrePaymentInterval.MONTHLY;
import static com.kgr.profitcalculation.domain.tax.TaxPrePaymentInterval.QUARTERLY;
import static com.kgr.profitcalculation.domain.tax.TaxPrePaymentInterval.YEARLY;
import static java.time.Month.AUGUST;
import static java.time.Month.FEBRUARY;

class ComponentTestDataFactory {

    static String anInvalidProfitCalculationRequest() {
        String request = "{\"businessTaxPrePayment\":{\"interval\":\"MONTHLY\"},\"incomeTaxPrePayment\":{\"amount\":60.0},\"solidarityTaxPrePayment\":{\"amount\":24.0,\"interval\":\"YEARLY\"},\"monthlyWorks\":[{\"ratePerHour\":80.0,\"hoursWorked\":40.0},{\"month\":\"AUGUST\",\"hoursWorked\":50.0}],\"fixedExpenses\":[{\"title\":\"xy\",\"amount\":30.0,\"startMonth\":2,\"endMonth\":11},{\"category\":\"PRIVATE\",\"amount\":20.0,\"startMonth\":null,\"endMonth\":null}]}";
        return request;
    }

    static String anErrorResponse() {
        return "{\"fixedExpenses[0].category\":\"must not be null\",\"fixedExpenses[1].title\":\"must not be null\",\"incomeTaxPrePayment.interval\":\"must not be null\",\"monthlyWorks[0].month\":\"must not be null\"}";
    }

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
}
