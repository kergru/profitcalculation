package com.kgr.profitcalculation.domain.tax;

import lombok.Getter;

import javax.money.MonetaryAmount;

@Getter
public abstract class Tax {

    protected final double taxRate;

    protected Tax(double taxRate) {
        this.taxRate = taxRate;
    }

    public abstract MonetaryAmount getTaxAmount(MonetaryAmount amount);

}
