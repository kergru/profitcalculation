package com.kgr.profitcalculation.domain.tax;

import javax.money.MonetaryAmount;

import static com.kgr.profitcalculation.domain.ProfitCalculator.ZERO;
import static com.kgr.profitcalculation.domain.ProfitCalculator.money;
import static javax.money.Monetary.getDefaultRounding;

public class BusinessTax extends Tax {

    private static final MonetaryAmount YEARLY_ALLOWANCE = money(24500);

    public BusinessTax(double taxRate) {
        super(taxRate);
    }

    @Override
    public MonetaryAmount getTaxAmount(MonetaryAmount netRevenue) {
        return getTaxAmount(netRevenue, YEARLY_ALLOWANCE.divide(12));
    }

    private MonetaryAmount getTaxAmount(MonetaryAmount netRevenue, MonetaryAmount allowance) {
        MonetaryAmount businessTaxAmount = ZERO;
        if (netRevenue.isGreaterThan(allowance)) {
            businessTaxAmount = netRevenue.subtract(allowance)
                    .multiply(0.035d).multiply(this.taxRate);
        }
        return businessTaxAmount.with(getDefaultRounding());
    }
}
