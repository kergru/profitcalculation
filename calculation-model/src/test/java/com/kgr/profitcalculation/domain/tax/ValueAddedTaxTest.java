package com.kgr.profitcalculation.domain.tax;

import org.junit.jupiter.api.Test;

import javax.money.MonetaryAmount;

import static com.kgr.profitcalculation.domain.ProfitCalculator.money;
import static org.assertj.core.api.Assertions.assertThat;

class ValueAddedTaxTest {

    ValueAddedTax valueAddedTax = new ValueAddedTax(19.0d);

    @Test
    void monthly_tax_amount_10000() {

        MonetaryAmount amount = valueAddedTax.getTaxAmount(money(1000));

        assertThat(amount).isEqualTo(money(190));
    }
}