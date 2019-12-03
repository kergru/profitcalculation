package com.kgr.profitcalculation.domain;

import com.kgr.profitcalculation.domain.tax.TaxPrePayment;

import java.time.Month;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

import static com.kgr.profitcalculation.domain.FixedMonthlyExpense.from;
import static com.kgr.profitcalculation.domain.ProfitCalculator.money;
import static com.kgr.profitcalculation.domain.tax.TaxPrePaymentInterval.MONTHLY;
import static java.time.Month.JANUARY;

public class TestdataFactory {

    static final int YEAR = 2019;

    static final Expense BUSINESS_EXPENSE1 = new Expense(ExpenseCategory.BUSINESS, "b1", money(10), null, null);
    static final FixedMonthlyExpense FIXED_MONTLY_EXPENSE1 = from(BUSINESS_EXPENSE1);

    static final Expense BUSINESS_EXPENSE2 = new Expense(ExpenseCategory.BUSINESS, "b2", money(30), null, null);
    static final FixedMonthlyExpense FIXED_BUSINESS_EXPENSE2 = from(BUSINESS_EXPENSE2);

    static final Expense PRIVATE_EXPENSE1 = new Expense(ExpenseCategory.PRIVATE, "p1", money(20), null, null);
    static final FixedMonthlyExpense FIXED_PRIVATE_EXPENSE1 = from(PRIVATE_EXPENSE1);

    static final Expense PRIVATE_EXPENSE2 = new Expense(ExpenseCategory.PRIVATE, "p2", money(40), null, null);
    static final FixedMonthlyExpense FIXED_PRIVATE_EXPENSE2 = from(PRIVATE_EXPENSE2);

    static List<Expense> EXPENSES = Arrays.asList(BUSINESS_EXPENSE1, BUSINESS_EXPENSE2, PRIVATE_EXPENSE1, PRIVATE_EXPENSE2);
    static List<FixedMonthlyExpense> BUSINESS_EXPENSES = Arrays.asList(FIXED_MONTLY_EXPENSE1, FIXED_BUSINESS_EXPENSE2);
    static List<FixedMonthlyExpense> PRIVATE_EXPENSES = Arrays.asList(FIXED_PRIVATE_EXPENSE1, FIXED_PRIVATE_EXPENSE2);

    static final MonthlyWork MONTHLY_WORK_NO_PROJECT = MonthlyWork.noProject(JANUARY);

    static final MonthlyWork MONTHLY_WORK_0h = new MonthlyWork(JANUARY, money(80), 0);

    static final MonthlyWork MONTHLY_WORK_10h = new MonthlyWork(JANUARY, money(80), 10d);

    static final MonthlyWork MONTHLY_WORK_100h = new MonthlyWork(JANUARY, money(80), 100d);

    static final ProfitCalculationCommand CALCULATION_PARAMETER_100h_NO_PREPAYMENT = new ProfitCalculationCommand(
            null, null, null, Arrays.asList(MONTHLY_WORK_100h), EXPENSES);

    static final ProfitCalculationCommand CALCULATION_PARAMETER_100h_LESS_PREPAYMENT = new ProfitCalculationCommand(
            taxPrePayment(20), taxPrePayment(100), taxPrePayment(10), Arrays.asList(MONTHLY_WORK_100h), EXPENSES);

    static final ProfitCalculationCommand CALCULATION_PARAMETER_100h_BIG_PREPAYMENT = new ProfitCalculationCommand(
            taxPrePayment(2000), taxPrePayment(5000), taxPrePayment(1000), Arrays.asList(MONTHLY_WORK_100h), EXPENSES);

    static MonthlyProfitCalculation monthlyProfitCalculation() {
        return MonthlyProfitCalculation.create(YearMonth.of(YEAR, JANUARY));
    }

    static TaxPrePayment taxPrePayment(Number value) {
        return new TaxPrePayment(money(value), MONTHLY);
    }

    static MonthlyProfitCalculation calculatedWithNoProject(Month month) {
        MonthlyProfitCalculation monthlyProfitCalculation = MonthlyProfitCalculation.create(YearMonth.of(YEAR, month));
        monthlyProfitCalculation.setFixedBusinessExpenses(BUSINESS_EXPENSES);
        monthlyProfitCalculation.setFixedPrivateExpenses(PRIVATE_EXPENSES);
        monthlyProfitCalculation.setNetRevenue(money(-40));
        monthlyProfitCalculation.setProfitAfterTax(money(-40));
        monthlyProfitCalculation.setProfitAfterTaxWithPrePaymentDifference(money(-40));
        monthlyProfitCalculation.setProfitAfterPrivateExpenses(money(-100));
        monthlyProfitCalculation.setProfitAfterPrivateExpensesWithPrePaymentDifference(money(-100));
        return monthlyProfitCalculation;
    }

    static MonthlyProfitCalculation calculatedWithWorkNoProject(Month month) {
        MonthlyProfitCalculation monthlyProfitCalculation = MonthlyProfitCalculation.create(YearMonth.of(YEAR, month));
        monthlyProfitCalculation.setFixedBusinessExpenses(BUSINESS_EXPENSES);
        monthlyProfitCalculation.setFixedPrivateExpenses(PRIVATE_EXPENSES);
        monthlyProfitCalculation.setRatePerHour(null);
        monthlyProfitCalculation.setNetRevenue(money(-40));
        monthlyProfitCalculation.setProfitAfterTax(money(-40));
        monthlyProfitCalculation.setProfitAfterTaxWithPrePaymentDifference(money(-40));
        monthlyProfitCalculation.setProfitAfterPrivateExpenses(money(-100));
        monthlyProfitCalculation.setProfitAfterPrivateExpensesWithPrePaymentDifference(money(-100));
        return monthlyProfitCalculation;
    }

    static MonthlyProfitCalculation calculatedWithWork0h(Month month) {
        MonthlyProfitCalculation monthlyProfitCalculation = MonthlyProfitCalculation.create(YearMonth.of(YEAR, month));
        monthlyProfitCalculation.setFixedBusinessExpenses(BUSINESS_EXPENSES);
        monthlyProfitCalculation.setFixedPrivateExpenses(PRIVATE_EXPENSES);
        monthlyProfitCalculation.setRatePerHour(money(80));
        monthlyProfitCalculation.setNetRevenue(money(-40));
        monthlyProfitCalculation.setProfitAfterTax(money(-40));
        monthlyProfitCalculation.setProfitAfterTaxWithPrePaymentDifference(money(-40));
        monthlyProfitCalculation.setProfitAfterPrivateExpenses(money(-100));
        monthlyProfitCalculation.setProfitAfterPrivateExpensesWithPrePaymentDifference(money(-100));
        return monthlyProfitCalculation;
    }

    static MonthlyProfitCalculation calculatedWithWork10h(Month month) {
        MonthlyProfitCalculation monthlyProfitCalculation = MonthlyProfitCalculation.create(YearMonth.of(YEAR, month));
        monthlyProfitCalculation.setFixedBusinessExpenses(BUSINESS_EXPENSES);
        monthlyProfitCalculation.setFixedPrivateExpenses(PRIVATE_EXPENSES);
        monthlyProfitCalculation.setHoursWorked(10d);
        monthlyProfitCalculation.setRatePerHour(money(80));
        monthlyProfitCalculation.setGrossPay(money(800));
        monthlyProfitCalculation.setGrossRevenue(money(952));
        monthlyProfitCalculation.setValueAddedTaxAmount(money(152));
        monthlyProfitCalculation.setNetRevenue(money(760));
        monthlyProfitCalculation.setProfitAfterTax(money(760));
        monthlyProfitCalculation.setProfitAfterTaxWithPrePaymentDifference(money(760));
        monthlyProfitCalculation.setProfitAfterPrivateExpenses(money(700));
        monthlyProfitCalculation.setProfitAfterPrivateExpensesWithPrePaymentDifference(money(700));
        return monthlyProfitCalculation;
    }

    static MonthlyProfitCalculation calculatedWithWork100h(Month month) {
        MonthlyProfitCalculation monthlyProfitCalculation = MonthlyProfitCalculation.create(YearMonth.of(YEAR, month));
        monthlyProfitCalculation.setFixedBusinessExpenses(BUSINESS_EXPENSES);
        monthlyProfitCalculation.setFixedPrivateExpenses(PRIVATE_EXPENSES);
        monthlyProfitCalculation.setHoursWorked(100d);
        monthlyProfitCalculation.setRatePerHour(money(80));
        monthlyProfitCalculation.setGrossPay(money(8000));
        monthlyProfitCalculation.setGrossRevenue(money(9520));
        monthlyProfitCalculation.setValueAddedTaxAmount(money(1520));
        monthlyProfitCalculation.setNetRevenue(money(7960));
        monthlyProfitCalculation.setBusinessTaxAmount(money(849.28));
        monthlyProfitCalculation.setIncomeTaxAmount(money(2235.18));
        monthlyProfitCalculation.setSolidarityTaxAmount(money(122.93));
        monthlyProfitCalculation.setProfitAfterTax(money(4752.61));
        monthlyProfitCalculation.setProfitAfterTaxWithPrePaymentDifference(money(4752.61));
        monthlyProfitCalculation.setProfitAfterPrivateExpenses(money(4692.61));
        monthlyProfitCalculation.setProfitAfterPrivateExpensesWithPrePaymentDifference(money(4692.61));
        return monthlyProfitCalculation;
    }

    static MonthlyProfitCalculation calculatedWithWork100hAndBigPrePayment(Month month) {
        MonthlyProfitCalculation monthlyProfitCalculation = MonthlyProfitCalculation.create(YearMonth.of(YEAR, month));
        monthlyProfitCalculation.setFixedBusinessExpenses(BUSINESS_EXPENSES);
        monthlyProfitCalculation.setFixedPrivateExpenses(PRIVATE_EXPENSES);
        monthlyProfitCalculation.setHoursWorked(100d);
        monthlyProfitCalculation.setRatePerHour(money(80));
        monthlyProfitCalculation.setGrossPay(money(8000));
        monthlyProfitCalculation.setGrossRevenue(money(9520));
        monthlyProfitCalculation.setValueAddedTaxAmount(money(1520));
        monthlyProfitCalculation.setNetRevenue(money(7960));
        monthlyProfitCalculation.setBusinessTaxAmount(money(849.28));
        monthlyProfitCalculation.setBussinesTaxPrePaymentDifference(money(1150.72));
        monthlyProfitCalculation.setIncomeTaxAmount(money(2235.18));
        monthlyProfitCalculation.setIncomeTaxPrePaymentDifference(money(1977.68));
        monthlyProfitCalculation.setSolidarityTaxAmount(money(122.93));
        monthlyProfitCalculation.setSolidarityTaxPrePaymentDifference(money(877.07));
        monthlyProfitCalculation.setProfitAfterTax(money(4752.61));
        monthlyProfitCalculation.setProfitAfterTaxWithPrePaymentDifference(money(747.14));
        monthlyProfitCalculation.setProfitAfterPrivateExpenses(money(4692.61));
        monthlyProfitCalculation.setProfitAfterPrivateExpensesWithPrePaymentDifference(money(687.14));
        return monthlyProfitCalculation;
    }

}
