package com.kgr.profitcalculation.domain;

import com.kgr.profitcalculation.domain.tax.BusinessTax;
import com.kgr.profitcalculation.domain.tax.IncomeTax;
import com.kgr.profitcalculation.domain.tax.SolidarityTax;
import com.kgr.profitcalculation.domain.tax.ValueAddedTax;

import javax.money.MonetaryAmount;

import static com.kgr.profitcalculation.domain.ProfitCalculator.ZERO;
import static javax.money.Monetary.getDefaultRounding;


public abstract class AbstractCalculator<C extends AbstractCalculation> {

    protected final C calculation;

    protected AbstractCalculator(C calculation) {
        this.calculation = calculation;
    }

    protected void calculate(ProfitCalculationCommand calculationParameter,
                             MonetaryAmount grossPay) {

        MonetaryAmount valueAddedTaxAmount = new ValueAddedTax(19.0d).getTaxAmount(grossPay);
        calculation.setValueAddedTaxAmount(valueAddedTaxAmount);

        MonetaryAmount grossRevenue = grossPay.add(valueAddedTaxAmount);
        calculation.setGrossRevenue(grossRevenue);

        MonetaryAmount netRevenue = grossPay.subtract(calculation.getSumFixedBusinessExpenses());
        calculation.setNetRevenue(netRevenue);

        MonetaryAmount businessTaxAmount = new BusinessTax(4.1d).getTaxAmount(netRevenue);
        calculation.setBusinessTaxAmount(businessTaxAmount);
        MonetaryAmount businessTaxPrePaymentDifference = getPrePaymentDifference(businessTaxAmount, getBusinessTaxPrePaymentAmount(calculationParameter));
        calculation.setBussinesTaxPrePaymentDifference(businessTaxPrePaymentDifference);

        MonetaryAmount incomeTaxAmount = new IncomeTax(42.0d).getTaxAmount(netRevenue);
        MonetaryAmount nettoIncomeTaxAmount = subtractBusinessTaxAllowance(incomeTaxAmount, businessTaxAmount);
        calculation.setIncomeTaxAmount(nettoIncomeTaxAmount);
        MonetaryAmount incomeTaxPrePaymentDifference = getPrePaymentDifference(incomeTaxAmount, getIncomeTaxPrePaymentAmount(calculationParameter));
        calculation.setIncomeTaxPrePaymentDifference(incomeTaxPrePaymentDifference);

        MonetaryAmount solidarityTaxAmount = new SolidarityTax(5.5d).getTaxAmount(nettoIncomeTaxAmount);
        calculation.setSolidarityTaxAmount(solidarityTaxAmount);
        MonetaryAmount solidarityPrePaymentDifference = getPrePaymentDifference(solidarityTaxAmount, getSolidarityTaxPrePaymentAmount(calculationParameter));
        calculation.setSolidarityTaxPrePaymentDifference(solidarityPrePaymentDifference);


        MonetaryAmount profitAfterTax = netRevenue.subtract(businessTaxAmount).subtract(nettoIncomeTaxAmount).subtract(solidarityTaxAmount);
        calculation.setProfitAfterTax(profitAfterTax);
        calculation.setProfitAfterTaxWithPrePaymentDifference(profitAfterTax
                .subtract(businessTaxPrePaymentDifference)
                .subtract(incomeTaxPrePaymentDifference)
                .subtract(solidarityPrePaymentDifference));

        MonetaryAmount profitAfterPrivateExpenses = profitAfterTax.subtract(calculation.getSumFixedPrivateExpenses());
        calculation.setProfitAfterPrivateExpenses(profitAfterPrivateExpenses);
        calculation.setProfitAfterPrivateExpensesWithPrePaymentDifference(profitAfterPrivateExpenses
                .subtract(businessTaxPrePaymentDifference)
                .subtract(incomeTaxPrePaymentDifference)
                .subtract(solidarityPrePaymentDifference));
    }

    protected abstract MonetaryAmount getBusinessTaxPrePaymentAmount(ProfitCalculationCommand calculationParameter);

    protected abstract MonetaryAmount getIncomeTaxPrePaymentAmount(ProfitCalculationCommand calculationParameter);

    protected abstract MonetaryAmount getSolidarityTaxPrePaymentAmount(ProfitCalculationCommand calculationParameter);

    private MonetaryAmount subtractBusinessTaxAllowance(MonetaryAmount incomeTaxAmount, MonetaryAmount businessTaxAmount) {
        MonetaryAmount allowance = businessTaxAmount.multiply(3.8d).divide(4.1d).with(getDefaultRounding());
        return incomeTaxAmount.isGreaterThan(allowance) ? incomeTaxAmount.subtract(allowance) : ZERO;
    }

    private MonetaryAmount getPrePaymentDifference(MonetaryAmount taxAmount, MonetaryAmount prePayment) {
        return prePayment != null && prePayment.isGreaterThan(taxAmount)
                ? prePayment.subtract(taxAmount)
                : ZERO;
    }
}
