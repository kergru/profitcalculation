package com.kgr.profitcalculation.domain.tax;

import javax.money.MonetaryAmount;

import static javax.money.Monetary.getDefaultRounding;

public class ValueAddedTax extends Tax {

    public ValueAddedTax(double taxRate) {
        super(taxRate);
    }

    @Override
    public MonetaryAmount getTaxAmount(MonetaryAmount grossPay) {
        return grossPay.multiply(this.taxRate).divide(100).with(getDefaultRounding());
    }
}
