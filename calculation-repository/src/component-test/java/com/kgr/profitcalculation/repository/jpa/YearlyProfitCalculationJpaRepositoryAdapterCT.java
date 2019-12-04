package com.kgr.profitcalculation.repository.jpa;

import com.kgr.profitcalculation.configuration.JpaRepositoryConfiguration;
import com.kgr.profitcalculation.domain.YearlyProfitCalculation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import static com.kgr.profitcalculation.repository.jpa.ComponentTestDataFactory.yearlyProfitCalculation;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@TestPropertySource(properties = {
        "calculation.cache=jpa",
})
@Import({JpaRepositoryConfiguration.class, YearlyCalculationMapper.class, TaxPrePaymentMapper.class,
        MonthlyCalculationMapper.class, FixedMonthlyExpenseMapper.class, CalculationValuesMapper.class})
class YearlyProfitCalculationJpaRepositoryAdapterCT {

    @Autowired
    YearlyProfitCalculationJpaRepositoryAdapter adapter;


    @Test
    void save() {
        YearlyProfitCalculation calculation = adapter.findByYear(2019);
        assertThat(calculation).isNull();

        adapter.save(yearlyProfitCalculation(2019));

        calculation = adapter.findByYear(2019);
        assertThat(calculation).isNotNull();
        assertThat(calculation.getYear()).isEqualTo(2019);
    }
}