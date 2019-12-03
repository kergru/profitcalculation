package com.kgr.profitcalculation.domain.tax;

import org.junit.jupiter.api.Test;

import javax.money.MonetaryAmount;

import static com.kgr.profitcalculation.domain.ProfitCalculator.ZERO;
import static com.kgr.profitcalculation.domain.ProfitCalculator.money;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaxPrePaymentTest {

    @Test
    void validate_prePayment_with_null_intervall() {

        assertThrows(IllegalArgumentException.class, () -> new TaxPrePayment(money(10), null).validate());
    }

    @Test
    void prePayment_zero() {

        MonetaryAmount amount = new TaxPrePayment(ZERO, TaxPrePaymentInterval.MONTHLY).getMonthlyTaxPrePaymentAmount();

        assertThat(amount).isEqualTo(ZERO);
    }

    @Test
    void prePayment_monthly() {

        MonetaryAmount amount = new TaxPrePayment(money(10), TaxPrePaymentInterval.MONTHLY).getMonthlyTaxPrePaymentAmount();

        assertThat(amount).isEqualTo(money(10));
    }

    @Test
    void prePayment_quarterly() {

        MonetaryAmount amount = new TaxPrePayment(money(30), TaxPrePaymentInterval.QUARTERLY).getMonthlyTaxPrePaymentAmount();

        assertThat(amount).isEqualTo(money(10));
    }

    @Test
    void prePayment_halfyearly() {

        MonetaryAmount amount = new TaxPrePayment(money(60), TaxPrePaymentInterval.HALFYEARLY).getMonthlyTaxPrePaymentAmount();

        assertThat(amount).isEqualTo(money(10));
    }

    @Test
    void prePayment_yearly() {

        MonetaryAmount amount = new TaxPrePayment(money(120), TaxPrePaymentInterval.YEARLY).getMonthlyTaxPrePaymentAmount();

        assertThat(amount).isEqualTo(money(10));
    }
}