package com.kgr.profitcalculation.domain.tax;

import lombok.Value;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

import static com.kgr.profitcalculation.domain.ProfitCalculator.ZERO;

@Value
public class TaxPrePayment {

    public static final TaxPrePayment ZERO_PRE_PAYMENT = new TaxPrePayment(ZERO, null);

    private final MonetaryAmount prePaymentAmount;

    private final TaxPrePaymentInterval taxPrePaymentInterval;

    public void validate() {
        if (prePaymentAmount.isGreaterThan(ZERO) && taxPrePaymentInterval == null) {
            throw new IllegalArgumentException("prePaymentAmount missing");
        }
    }

    public MonetaryAmount getMonthlyTaxPrePaymentAmount() {
        MonetaryAmount amount = ZERO;
        if (taxPrePaymentInterval != null) {
            switch (taxPrePaymentInterval) {
                case MONTHLY:
                    amount = prePaymentAmount;
                    break;
                case QUARTERLY:
                    amount = prePaymentAmount.divide(3);
                    break;
                case HALFYEARLY:
                    amount = prePaymentAmount.divide(6);
                    break;
                case YEARLY:
                    amount = prePaymentAmount.divide(12);
                    break;
                default:
            }
        }
        return amount.with(Monetary.getDefaultRounding());
    }
}
