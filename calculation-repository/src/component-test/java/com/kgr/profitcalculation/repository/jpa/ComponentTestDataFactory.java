package com.kgr.profitcalculation.repository.jpa;

import com.kgr.profitcalculation.domain.FixedMonthlyExpense;
import com.kgr.profitcalculation.domain.MonthlyProfitCalculation;
import com.kgr.profitcalculation.domain.YearlyProfitCalculation;
import com.kgr.profitcalculation.domain.tax.TaxPrePayment;

import java.time.YearMonth;
import java.util.Arrays;

import static com.kgr.profitcalculation.domain.ProfitCalculator.money;
import static com.kgr.profitcalculation.domain.tax.TaxPrePaymentInterval.MONTHLY;
import static java.time.Month.JANUARY;

class ComponentTestDataFactory {

    static YearlyProfitCalculation yearlyProfitCalculation(int year) {
        YearlyProfitCalculation calculation = YearlyProfitCalculation.create(year);
        calculation.setBusinessTaxPrePayment(new TaxPrePayment(money(100), MONTHLY));
        MonthlyProfitCalculation monthlyProfitCalculation = calculation.getMonthlyProfitCalculation(YearMonth.of(year, JANUARY));
        monthlyProfitCalculation.setRatePerHour(money(80));
        monthlyProfitCalculation.setHoursWorked(100);
        monthlyProfitCalculation.setFixedBusinessExpenses(Arrays.asList(new FixedMonthlyExpense("insurance", money(40))));
        return calculation;
    }
}
