package com.kgr.profitcalculation.domain;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.money.MonetaryAmount;
import java.time.Month;
import java.util.Arrays;

import static com.kgr.profitcalculation.domain.MonthlyCalculationAssertions.assertMonthlyCalculation;
import static com.kgr.profitcalculation.domain.ProfitCalculator.ZERO;
import static com.kgr.profitcalculation.domain.ProfitCalculator.money;
import static com.kgr.profitcalculation.domain.TestdataFactory.CALCULATION_PARAMETER_100h_LESS_PREPAYMENT;
import static com.kgr.profitcalculation.domain.TestdataFactory.CALCULATION_PARAMETER_100h_NO_PREPAYMENT;
import static com.kgr.profitcalculation.domain.TestdataFactory.EXPENSES;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class YearlyProfitCalculatorTest {

    YearlyProfitCalculator calculator;

    YearlyProfitCalculation calculation;

    @BeforeEach
    void init() {
        calculation = YearlyProfitCalculation.create(2019);
        calculator = new YearlyProfitCalculator(calculation);
    }

    @Test
    void calculate() {

        calculator.calculate(new ProfitCalculationCommand(
                null, null, null,
                Arrays.asList(
                        new MonthlyWork(Month.JANUARY, money(80), 100),
                        new MonthlyWork(Month.FEBRUARY, money(80), 100),
                        new MonthlyWork(Month.MARCH, money(80), 100),
                        new MonthlyWork(Month.APRIL, money(80), 100),
                        new MonthlyWork(Month.MAY, money(80), 100),
                        new MonthlyWork(Month.JUNE, money(80), 100),
                        new MonthlyWork(Month.SEPTEMBER, money(80), 100),
                        new MonthlyWork(Month.OCTOBER, money(80), 100),
                        new MonthlyWork(Month.NOVEMBER, money(80), 100)),
                EXPENSES));

        assertCalculation(calculation, calculation);
    }

    @Test
    void getBusinessTaxPrePaymentAmount_returns_yearly_amount() {

        MonetaryAmount businessTaxPrePaymentAmount = calculator.getBusinessTaxPrePaymentAmount(CALCULATION_PARAMETER_100h_LESS_PREPAYMENT);

        assertThat(businessTaxPrePaymentAmount).isEqualTo(money(240));
    }

    @Test
    void getBusinessTaxPrePaymentAmount_returns_zero_for_no_prepayment() {

        MonetaryAmount businessTaxPrePaymentAmount = calculator.getBusinessTaxPrePaymentAmount(CALCULATION_PARAMETER_100h_NO_PREPAYMENT);

        assertThat(businessTaxPrePaymentAmount).isEqualTo(ZERO);
    }

    @Test
    void getIncomeTaxPrePaymentAmount_returns_yearly_amount() {

        MonetaryAmount incomeTaxPrePaymentAmount = calculator.getIncomeTaxPrePaymentAmount(CALCULATION_PARAMETER_100h_LESS_PREPAYMENT);

        assertThat(incomeTaxPrePaymentAmount).isEqualTo(money(1200));
    }

    @Test
    void getIncomeTaxPrePaymentAmount_returns_zero_for_no_prepayment() {

        MonetaryAmount incomeTaxPrePaymentAmount = calculator.getIncomeTaxPrePaymentAmount(CALCULATION_PARAMETER_100h_NO_PREPAYMENT);

        assertThat(incomeTaxPrePaymentAmount).isEqualTo(ZERO);
    }

    @Test
    void getSolidarityTaxPrePaymentAmount_returns_yearly_amount() {

        MonetaryAmount solidarityTaxPrePaymentAmount = calculator.getSolidarityTaxPrePaymentAmount(CALCULATION_PARAMETER_100h_LESS_PREPAYMENT);

        assertThat(solidarityTaxPrePaymentAmount).isEqualTo(money(120));
    }

    @Test
    void getSolidarityTaxPrePaymentAmount_returns_zero_for_no_prepayment() {

        MonetaryAmount solidarityTaxPrePaymentAmount = calculator.getSolidarityTaxPrePaymentAmount(CALCULATION_PARAMETER_100h_NO_PREPAYMENT);

        assertThat(solidarityTaxPrePaymentAmount).isEqualTo(ZERO);
    }


    private void assertCalculation(YearlyProfitCalculation actual, YearlyProfitCalculation expected) {
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actual.getYear()).as("year").isEqualTo(expected.getYear());
        softly.assertThat(actual.getBusinessTaxPrePayment()).as("BusinessTaxPrePayment").isEqualTo(expected.getBusinessTaxPrePayment());
        softly.assertThat(actual.getIncomeTaxPrePayment()).as("IncomeTaxPrePayment").isEqualTo(expected.getIncomeTaxPrePayment());
        softly.assertThat(actual.getSolidarityTaxPrePayment()).as("SolidarityTaxPrePayment").isEqualTo(expected.getSolidarityTaxPrePayment());

        for (MonthlyProfitCalculation monthlyProfitCalculation : actual.getMonthlyProfitCalculations()) {
            assertMonthlyCalculation(softly, monthlyProfitCalculation, expected.getMonthlyProfitCalculation(monthlyProfitCalculation.getYearMonth()));
        }
    }
}