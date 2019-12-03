package com.kgr.profitcalculation.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.money.MonetaryAmount;

import static com.kgr.profitcalculation.domain.ProfitCalculator.ZERO;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public abstract class AbstractCalculation {

    private double hoursWorked;

    private MonetaryAmount grossPay = ZERO;

    private MonetaryAmount grossRevenue = ZERO;

    private MonetaryAmount valueAddedTaxAmount = ZERO;

    private MonetaryAmount netRevenue = ZERO;

    private MonetaryAmount businessTaxAmount = ZERO;

    private MonetaryAmount bussinesTaxPrePaymentDifference = ZERO;

    private MonetaryAmount incomeTaxAmount = ZERO;

    private MonetaryAmount incomeTaxPrePaymentDifference = ZERO;

    private MonetaryAmount solidarityTaxAmount = ZERO;

    private MonetaryAmount solidarityTaxPrePaymentDifference = ZERO;

    private MonetaryAmount profitAfterTax = ZERO;

    private MonetaryAmount profitAfterTaxWithPrePaymentDifference = ZERO;

    private MonetaryAmount profitAfterPrivateExpenses = ZERO;

    private MonetaryAmount profitAfterPrivateExpensesWithPrePaymentDifference = ZERO;

    public abstract MonetaryAmount getSumFixedBusinessExpenses();

    public abstract MonetaryAmount getSumFixedPrivateExpenses();
}
