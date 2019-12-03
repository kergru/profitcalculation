package com.kgr.profitcalculation.domain;

import com.kgr.profitcalculation.repository.YearlyProfitCalculationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.money.MonetaryAmount;

import static com.kgr.profitcalculation.domain.ProfitCalculator.money;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class ProfitCalculatorTest {

    @Mock
    private YearlyProfitCalculationRepository yearlyProfitCalculationRepository;

    @Mock
    YearlyProfitCalculation yearlyProfitCalculation;

    private ProfitCalculator profitCalculator;

    @BeforeEach
    void setup() {
        lenient().doReturn(yearlyProfitCalculation).when(yearlyProfitCalculationRepository).findByYear(2019);
        profitCalculator = new ProfitCalculator(yearlyProfitCalculationRepository);
    }

    @Test
    void money_returns_amount() {

        MonetaryAmount money = money(22.46d);

        assertThat(money.getNumber().doubleValue()).isEqualTo(22.46d);
        assertThat(money.getCurrency().getCurrencyCode()).isEqualTo("EUR");
    }

    @Test
    void get_calculation_returns_stored_calculation() {

        YearlyProfitCalculation calculation = profitCalculator.getCalculation(2019);

        assertThat(calculation).isEqualTo(yearlyProfitCalculation);
    }

    @Test
    void get_calculation_returns_new_calculation() {

        YearlyProfitCalculation calculation = profitCalculator.getCalculation(2018);

        assertThat(calculation).isNotEqualTo(yearlyProfitCalculation);
        assertThat(calculation.getYear()).isEqualTo(2018);
    }
}