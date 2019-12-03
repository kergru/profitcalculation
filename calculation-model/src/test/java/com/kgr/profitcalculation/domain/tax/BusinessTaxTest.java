package com.kgr.profitcalculation.domain.tax;

import org.junit.jupiter.api.Test;

import javax.money.MonetaryAmount;

import static com.kgr.profitcalculation.domain.ProfitCalculator.ZERO;
import static com.kgr.profitcalculation.domain.ProfitCalculator.money;
import static org.assertj.core.api.Assertions.assertThat;

class BusinessTaxTest {

    BusinessTax businessTax = new BusinessTax(4.1d);

    @Test
    void monthly_tax_amount_under_allowance() {

        MonetaryAmount amount = businessTax.getTaxAmount(money(2041));

        assertThat(amount).isEqualTo(ZERO);
    }

    @Test
    void monthly_tax_amount_over_allowance() {

        MonetaryAmount amount = businessTax.getTaxAmount(money(2042));

        assertThat(amount).isEqualTo(money(0.05d));
    }

    @Test
    void monthly_tax_amount_10000() {

        MonetaryAmount amount = businessTax.getTaxAmount(money(10000));

        assertThat(amount).isEqualTo(money(1142.02d));
    }
}