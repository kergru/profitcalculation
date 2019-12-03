package com.kgr.profitcalculation.domain.tax;

import org.junit.jupiter.api.Test;

import javax.money.MonetaryAmount;

import static com.kgr.profitcalculation.domain.ProfitCalculator.ZERO;
import static com.kgr.profitcalculation.domain.ProfitCalculator.money;
import static org.assertj.core.api.Assertions.assertThat;

class IncomeTaxTest {

    IncomeTax incomeTax = new IncomeTax(42.0d);

    @Test
    void monthly_tax_amount_under_allowance() {

        MonetaryAmount amount = incomeTax.getTaxAmount(money(764));

        assertThat(amount).isEqualTo(ZERO);
    }

    @Test
    void monthly_tax_amount_over_allowance() {

        MonetaryAmount amount = incomeTax.getTaxAmount(money(765));

        assertThat(amount).isEqualTo(money(0.42d));
    }

    @Test
    void monthly_tax_amount_10000() {

        MonetaryAmount amount = incomeTax.getTaxAmount(money(10000));

        assertThat(amount).isEqualTo(money(3879.12d));
    }
}