package com.kgr.profitcalculation.domain.tax;

import javax.money.MonetaryAmount;

import static com.kgr.profitcalculation.domain.ProfitCalculator.ZERO;
import static com.kgr.profitcalculation.domain.ProfitCalculator.money;
import static javax.money.Monetary.getDefaultRounding;

public class IncomeTax extends Tax {

    private static final MonetaryAmount YEARLY_ALLOWANCE = money(9168);

    public IncomeTax(double taxRate) {
        super(taxRate);
    }

    @Override
    public MonetaryAmount getTaxAmount(MonetaryAmount netRevenue) {
        return getTaxAmount(netRevenue, YEARLY_ALLOWANCE.divide(12));
    }

    public MonetaryAmount getYearlyTaxAmount(MonetaryAmount netRevenue) {
        return getTaxAmount(netRevenue, YEARLY_ALLOWANCE);
    }

    private MonetaryAmount getTaxAmount(MonetaryAmount netRevenue, MonetaryAmount allowance) {
        MonetaryAmount incomeTaxAmount = ZERO;
        if (netRevenue.isGreaterThan(allowance)) {
            incomeTaxAmount = netRevenue.subtract(allowance)
                    .multiply(taxRate).divide(100);
        }
        return incomeTaxAmount.with(getDefaultRounding());
    }
}
