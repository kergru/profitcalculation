package com.kgr.profitcalculation.domain;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static com.kgr.profitcalculation.domain.MonthlyCalculationAssertions.assertMonthlyCalculation;
import static com.kgr.profitcalculation.domain.TestdataFactory.CALCULATION_PARAMETER_100h_BIG_PREPAYMENT;
import static com.kgr.profitcalculation.domain.TestdataFactory.CALCULATION_PARAMETER_100h_LESS_PREPAYMENT;
import static com.kgr.profitcalculation.domain.TestdataFactory.EXPENSES;
import static com.kgr.profitcalculation.domain.TestdataFactory.MONTHLY_WORK_0h;
import static com.kgr.profitcalculation.domain.TestdataFactory.MONTHLY_WORK_100h;
import static com.kgr.profitcalculation.domain.TestdataFactory.MONTHLY_WORK_10h;
import static com.kgr.profitcalculation.domain.TestdataFactory.MONTHLY_WORK_NO_PROJECT;
import static com.kgr.profitcalculation.domain.TestdataFactory.calculatedWithWork0h;
import static com.kgr.profitcalculation.domain.TestdataFactory.calculatedWithWork100h;
import static com.kgr.profitcalculation.domain.TestdataFactory.calculatedWithWork100hAndBigPrePayment;
import static com.kgr.profitcalculation.domain.TestdataFactory.calculatedWithWork10h;
import static com.kgr.profitcalculation.domain.TestdataFactory.calculatedWithWorkNoProject;
import static com.kgr.profitcalculation.domain.TestdataFactory.monthlyProfitCalculation;
import static java.time.Month.JANUARY;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class MonthlyProfitCalculatorTest {

    MonthlyProfitCalculator calculator;

    MonthlyProfitCalculation calculation;

    @BeforeEach
    void init() {
        calculation = monthlyProfitCalculation();
        calculator = new MonthlyProfitCalculator(calculation);
    }

    @Test
    void calculate_fails_with_null_calculationparameter() {

        assertThrows(AssertionError.class, () -> {
            calculator.calculate(null);
        });
    }

    @Test
    void calculate_with_no_project() {

        calculator.calculate(new ProfitCalculationCommand(
                null, null, null, Arrays.asList(MONTHLY_WORK_NO_PROJECT), EXPENSES));

        assertCalculation(calculation, calculatedWithWorkNoProject(JANUARY));
    }

    @Test
    void calculate_with_work_0h() {

        calculator.calculate(new ProfitCalculationCommand(
                null, null, null, Arrays.asList(MONTHLY_WORK_0h), EXPENSES));

        assertCalculation(calculation, calculatedWithWork0h(JANUARY));
    }

    @Test
    void calculate_with_work_10h() {

        calculator.calculate(new ProfitCalculationCommand(
                null, null, null, Arrays.asList(MONTHLY_WORK_10h), EXPENSES));

        assertCalculation(calculation, calculatedWithWork10h(JANUARY));
    }

    @Test
    void calculate_with_work_100h_and_no_prePayments() {

        calculator.calculate(new ProfitCalculationCommand(
                null, null, null, Arrays.asList(MONTHLY_WORK_100h), EXPENSES));

        assertCalculation(calculation, calculatedWithWork100h(JANUARY));
    }

    @Test
    void calculate_with_work_100h_and_prePayments_less_then_real_tax_amounts() {

        calculator.calculate(CALCULATION_PARAMETER_100h_LESS_PREPAYMENT);

        assertCalculation(calculation, calculatedWithWork100h(JANUARY));
    }

    @Test
    void calculate_with_work_100h_and_prePayments_greater_then_real_tax_amounts() {

        calculator.calculate(CALCULATION_PARAMETER_100h_BIG_PREPAYMENT);

        assertCalculation(calculation, calculatedWithWork100hAndBigPrePayment(JANUARY));
    }

    private void assertCalculation(MonthlyProfitCalculation actual, MonthlyProfitCalculation expected) {
        SoftAssertions softly = new SoftAssertions();
        assertMonthlyCalculation(softly, actual, expected);
        softly.assertAll();
    }
}