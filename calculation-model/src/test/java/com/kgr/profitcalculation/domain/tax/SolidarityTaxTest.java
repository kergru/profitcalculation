package com.kgr.profitcalculation.domain.tax;

import org.junit.jupiter.api.Test;

import javax.money.MonetaryAmount;

import static com.kgr.profitcalculation.domain.ProfitCalculator.money;
import static org.assertj.core.api.Assertions.assertThat;

class SolidarityTaxTest {

    SolidarityTax solidarityTax = new SolidarityTax(5.5d);

    @Test
    void monthly_tax_amount_10000() {

        MonetaryAmount amount = solidarityTax.getTaxAmount(money(1000));

        assertThat(amount).isEqualTo(money(55));
    }
}