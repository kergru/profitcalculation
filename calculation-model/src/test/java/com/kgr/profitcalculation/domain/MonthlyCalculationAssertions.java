package com.kgr.profitcalculation.domain;

import org.assertj.core.api.SoftAssertions;

class MonthlyCalculationAssertions {

    static void assertMonthlyCalculation(SoftAssertions softly, MonthlyProfitCalculation actual, MonthlyProfitCalculation expected) {
        softly.assertThat(actual.getYearMonth()).as("YearMonth").isEqualTo(expected.getYearMonth());
        softly.assertThat(actual.getRatePerHour()).as("Rate/hour").isEqualTo(expected.getRatePerHour());
        softly.assertThat(actual.getHoursWorked()).as("Hours worked").isEqualTo(expected.getHoursWorked());
        softly.assertThat(actual.getFixedBusinessExpenses()).as("FixedBusinessExpenses").isEqualTo(expected.getFixedBusinessExpenses());
        softly.assertThat(actual.getFixedPrivateExpenses()).as("FixedPrivateExpenses").isEqualTo(expected.getFixedPrivateExpenses());
        softly.assertThat(actual.getBusinessTaxAmount()).as("BusinessTaxAmount").isEqualTo(expected.getBusinessTaxAmount());
        softly.assertThat(actual.getBussinesTaxPrePaymentDifference()).as("BussinesTaxPrePaymentDifference").isEqualTo(expected.getBussinesTaxPrePaymentDifference());
        softly.assertThat(actual.getGrossPay()).as("GrossPay").isEqualTo(expected.getGrossPay());
        softly.assertThat(actual.getGrossRevenue()).as("GrossRevenue").isEqualTo(expected.getGrossRevenue());
        softly.assertThat(actual.getNetRevenue()).as("NetRevenue").isEqualTo(expected.getNetRevenue());
        softly.assertThat(actual.getHoursWorked()).as("HoursWorked").isEqualTo(expected.getHoursWorked());
        softly.assertThat(actual.getIncomeTaxAmount()).as("IncomeTaxAmount").isEqualTo(expected.getIncomeTaxAmount());
        softly.assertThat(actual.getIncomeTaxPrePaymentDifference()).as("IncomeTaxPrePaymentDifference").isEqualTo(expected.getIncomeTaxPrePaymentDifference());
        softly.assertThat(actual.getProfitAfterPrivateExpenses()).as("ProfitAfterPrivateExpenses").isEqualTo(expected.getProfitAfterPrivateExpenses());
        softly.assertThat(actual.getProfitAfterPrivateExpensesWithPrePaymentDifference()).as("ProfitAfterPrivateExpensesWithPrePaymentDifference").isEqualTo(expected.getProfitAfterPrivateExpensesWithPrePaymentDifference());
        softly.assertThat(actual.getProfitAfterTax()).as("ProfitAfterTax").isEqualTo(expected.getProfitAfterTax());
        softly.assertThat(actual.getProfitAfterTaxWithPrePaymentDifference()).as("ProfitAfterTaxWithPrePaymentDifference").isEqualTo(expected.getProfitAfterTaxWithPrePaymentDifference());
        softly.assertThat(actual.getSolidarityTaxAmount()).as("SolidarityTaxAmount").isEqualTo(expected.getSolidarityTaxAmount());
        softly.assertThat(actual.getSolidarityTaxPrePaymentDifference()).as("SolidarityTaxPrePaymentDifference").isEqualTo(expected.getSolidarityTaxPrePaymentDifference());
        softly.assertThat(actual.getValueAddedTaxAmount()).as("ValueAddedTaxAmount").isEqualTo(expected.getValueAddedTaxAmount());
    }
}
