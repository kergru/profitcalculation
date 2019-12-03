package com.kgr.profitcalculation.console.adapter;

import com.kgr.profitcalculation.domain.MonthlyProfitCalculation;
import com.kgr.profitcalculation.domain.YearlyProfitCalculation;
import org.springframework.shell.table.ArrayTableModel;
import org.springframework.shell.table.TableModel;

import java.time.Month;

public class ProfitCalculationModelConverter {

    public TableModel convert(YearlyProfitCalculation calculation) {
        Object[][] data = new Object[17][14];

        setHeader(calculation, data);
        setHourlyRate(calculation, data, 1);
        setHoursWorked(calculation, data, 2);
        setGrossPay(calculation, data, 3);
        setValueAddedTaxAmount(calculation, data, 4);
        setGrossRevenue(calculation, data, 5);
        setFixedBusinessExpenses(calculation, data, 6);
        setNetRevene(calculation, data, 7);
        setBusinessTaxAmount(calculation, data, 8);
        setBussinesTaxPrePaymentDifference(calculation, data, 9);
        setIncomeTaxAmount(calculation, data, 10);
        setIncomeTaxPrePaymentDifference(calculation, data, 11);
        setSolidarityTaxAmount(calculation, data, 12);
        setSolidarityTaxPrePaymentDifference(calculation, data, 13);
        setProfitAfterTax(calculation, data, 14);
        setFixedPrivateExpenses(calculation, data, 15);
        setProfitAfterPrivateExpenses(calculation, data, 16);

        return new ArrayTableModel(data);
    }

    private void setProfitAfterPrivateExpenses(YearlyProfitCalculation calculation, Object[][] data, int row) {
        data[row][0] = "Profit after Private Expenses";
        int i = 1;
        for (MonthlyProfitCalculation monthlyProfitCalculation : calculation.getMonthlyProfitCalculations()) {
            data[row][i++] = monthlyProfitCalculation.getProfitAfterPrivateExpensesWithPrePaymentDifference();
        }
        data[row][i++] = calculation.getProfitAfterPrivateExpenses();
    }

    private void setFixedPrivateExpenses(YearlyProfitCalculation calculation, Object[][] data, int row) {
        data[row][0] = "Private Expenses";
        int i = 1;
        for (MonthlyProfitCalculation monthlyProfitCalculation : calculation.getMonthlyProfitCalculations()) {
            data[row][i++] = monthlyProfitCalculation.getSumFixedPrivateExpenses();
        }
        data[row][i++] = calculation.getSumFixedPrivateExpenses();
    }

    private void setProfitAfterTax(YearlyProfitCalculation calculation, Object[][] data, int row) {
        data[row][0] = "Profit after Tax";
        int i = 1;
        for (MonthlyProfitCalculation monthlyProfitCalculation : calculation.getMonthlyProfitCalculations()) {
            data[row][i++] = monthlyProfitCalculation.getProfitAfterTaxWithPrePaymentDifference();
        }
        data[row][i++] = calculation.getProfitAfterTax();
    }

    private void setSolidarityTaxPrePaymentDifference(YearlyProfitCalculation calculation, Object[][] data, int row) {
        data[row][0] = "Solidarity Tax Prepayment";
        int i = 1;
        for (MonthlyProfitCalculation monthlyProfitCalculation : calculation.getMonthlyProfitCalculations()) {
            data[row][i++] = monthlyProfitCalculation.getSolidarityTaxPrePaymentDifference();
        }
        data[row][i++] = calculation.getSolidarityTaxPrePaymentDifference();
    }

    private void setSolidarityTaxAmount(YearlyProfitCalculation calculation, Object[][] data, int row) {
        data[row][0] = "Solidarity Tax";
        int i = 1;
        for (MonthlyProfitCalculation monthlyProfitCalculation : calculation.getMonthlyProfitCalculations()) {
            data[row][i++] = monthlyProfitCalculation.getSolidarityTaxAmount();
        }
        data[row][i++] = calculation.getSolidarityTaxAmount();
    }

    private void setIncomeTaxPrePaymentDifference(YearlyProfitCalculation calculation, Object[][] data, int row) {
        data[row][0] = "Income Tax Prepayment";
        int i = 1;
        for (MonthlyProfitCalculation monthlyProfitCalculation : calculation.getMonthlyProfitCalculations()) {
            data[row][i++] = monthlyProfitCalculation.getIncomeTaxPrePaymentDifference();
        }
        data[row][i++] = calculation.getIncomeTaxPrePaymentDifference();
    }

    private void setIncomeTaxAmount(YearlyProfitCalculation calculation, Object[][] data, int row) {
        data[row][0] = "Income Tax";
        int i = 1;
        for (MonthlyProfitCalculation monthlyProfitCalculation : calculation.getMonthlyProfitCalculations()) {
            data[row][i++] = monthlyProfitCalculation.getIncomeTaxAmount();
        }
        data[row][i++] = calculation.getIncomeTaxAmount();
    }

    private void setBussinesTaxPrePaymentDifference(YearlyProfitCalculation calculation, Object[][] data, int row) {
        data[row][0] = "Business Tax Prepayment";
        int i = 1;
        for (MonthlyProfitCalculation monthlyProfitCalculation : calculation.getMonthlyProfitCalculations()) {
            data[row][i++] = monthlyProfitCalculation.getBussinesTaxPrePaymentDifference();
        }
        data[row][i++] = calculation.getBussinesTaxPrePaymentDifference();
    }

    private void setBusinessTaxAmount(YearlyProfitCalculation calculation, Object[][] data, int row) {
        data[row][0] = "Business Tax";
        int i = 1;
        for (MonthlyProfitCalculation monthlyProfitCalculation : calculation.getMonthlyProfitCalculations()) {
            data[row][i++] = monthlyProfitCalculation.getBusinessTaxAmount();
        }
        data[row][i++] = calculation.getBusinessTaxAmount();
    }

    private void setNetRevene(YearlyProfitCalculation calculation, Object[][] data, int row) {
        data[row][0] = "Net Revenue";
        int i = 1;
        for (MonthlyProfitCalculation monthlyProfitCalculation : calculation.getMonthlyProfitCalculations()) {
            data[row][i++] = monthlyProfitCalculation.getNetRevenue();
        }
        data[row][i++] = calculation.getNetRevenue();
    }

    private void setFixedBusinessExpenses(YearlyProfitCalculation calculation, Object[][] data, int row) {
        data[row][0] = "Business Expenses";
        int i = 1;
        for (MonthlyProfitCalculation monthlyProfitCalculation : calculation.getMonthlyProfitCalculations()) {
            data[row][i++] = monthlyProfitCalculation.getSumFixedBusinessExpenses();
        }
        data[row][i++] = calculation.getSumFixedBusinessExpenses();
    }

    private void setGrossRevenue(YearlyProfitCalculation calculation, Object[][] data, int row) {
        data[row][0] = "Gross Reveneue";
        int i = 1;
        for (MonthlyProfitCalculation monthlyProfitCalculation : calculation.getMonthlyProfitCalculations()) {
            data[row][i++] = monthlyProfitCalculation.getGrossRevenue();
        }
        data[row][i++] = calculation.getGrossRevenue();
    }

    private void setValueAddedTaxAmount(YearlyProfitCalculation calculation, Object[][] data, int row) {
        data[row][0] = "VAT";
        int i = 1;
        for (MonthlyProfitCalculation monthlyProfitCalculation : calculation.getMonthlyProfitCalculations()) {
            data[row][i++] = monthlyProfitCalculation.getValueAddedTaxAmount();
        }
        data[row][i++] = calculation.getValueAddedTaxAmount();
    }

    private void setGrossPay(YearlyProfitCalculation calculation, Object[][] data, int row) {
        data[row][0] = "Gross Pay";
        int i = 1;
        for (MonthlyProfitCalculation monthlyProfitCalculation : calculation.getMonthlyProfitCalculations()) {
            data[row][i++] = monthlyProfitCalculation.getGrossPay();
        }
        data[row][i++] = calculation.getGrossPay();
    }

    private void setHoursWorked(YearlyProfitCalculation calculation, Object[][] data, int row) {
        data[row][0] = "Hours worked";
        int i = 1;
        for (MonthlyProfitCalculation monthlyProfitCalculation : calculation.getMonthlyProfitCalculations()) {
            data[row][i++] = monthlyProfitCalculation.getHoursWorked();
        }
        data[row][i++] = calculation.getHoursWorked();
    }

    private void setHourlyRate(YearlyProfitCalculation calculation, Object[][] data, int row) {
        data[row][0] = "Rate/hour";
        int i = 1;
        for (MonthlyProfitCalculation monthlyProfitCalculation : calculation.getMonthlyProfitCalculations()) {
            data[row][i++] = monthlyProfitCalculation.getRatePerHour();
        }
        data[row][i++] = "";
    }

    private void setHeader(YearlyProfitCalculation calculation, Object[][] data) {
        data[0][0] = calculation.getYear();
        int i = 1;
        for (Month month : Month.values()) {
            data[0][i++] = month;
        }
        data[0][i] = "Total";
    }
}

