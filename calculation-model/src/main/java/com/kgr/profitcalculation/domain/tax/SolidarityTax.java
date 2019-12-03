package com.kgr.profitcalculation.domain.tax;

import javax.money.MonetaryAmount;

import static javax.money.Monetary.getDefaultRounding;

public class SolidarityTax extends Tax {

    public SolidarityTax(double taxRate) {
        super(taxRate);
    }

    @Override
    public MonetaryAmount getTaxAmount(MonetaryAmount incomeTaxAmount) {
        return incomeTaxAmount.multiply(this.taxRate).divide(100).with(getDefaultRounding());
    }
}
