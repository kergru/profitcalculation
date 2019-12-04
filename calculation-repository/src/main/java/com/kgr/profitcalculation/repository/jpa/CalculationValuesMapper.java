package com.kgr.profitcalculation.repository.jpa;

import com.kgr.profitcalculation.domain.AbstractCalculation;
import com.kgr.profitcalculation.repository.jpa.entity.CalculationValuesEntity;
import org.springframework.stereotype.Component;

import static com.kgr.profitcalculation.domain.ProfitCalculator.money;

@Component
public class CalculationValuesMapper {

    void toDomain(AbstractCalculation domain, CalculationValuesEntity entity) {
        domain.setHoursWorked(entity.getHoursWorked());
        domain.setGrossPay(money(entity.getGrossPay()));
        domain.setGrossRevenue(money(entity.getGrossRevenue()));
        domain.setValueAddedTaxAmount(money(entity.getValueAddedTaxAmount()));
        domain.setNetRevenue(money(entity.getNetRevenue()));
        domain.setBusinessTaxAmount(money(entity.getBusinessTaxAmount()));
        domain.setBussinesTaxPrePaymentDifference(money(entity.getBussinesTaxPrePaymentDifference()));
        domain.setIncomeTaxAmount(money(entity.getIncomeTaxAmount()));
        domain.setIncomeTaxPrePaymentDifference(money(entity.getIncomeTaxPrePaymentDifference()));
        domain.setSolidarityTaxAmount(money(entity.getSolidarityTaxAmount()));
        domain.setSolidarityTaxPrePaymentDifference(money(entity.getSolidarityTaxPrePaymentDifference()));
        domain.setProfitAfterTax(money(entity.getProfitAfterTax()));
        domain.setProfitAfterTaxWithPrePaymentDifference(money(entity.getProfitAfterTaxWithPrePaymentDifference()));
        domain.setProfitAfterPrivateExpenses(money(entity.getProfitAfterPrivateExpenses()));
        domain.setProfitAfterPrivateExpensesWithPrePaymentDifference(money(entity.getProfitAfterPrivateExpensesWithPrePaymentDifference()));
    }

    CalculationValuesEntity toEntity(AbstractCalculation domain) {
        CalculationValuesEntity entity = new CalculationValuesEntity();
        entity.setHoursWorked(domain.getHoursWorked());
        entity.setGrossPay(domain.getGrossPay().getNumber().doubleValue());
        entity.setGrossRevenue(domain.getGrossRevenue().getNumber().doubleValue());
        entity.setValueAddedTaxAmount(domain.getValueAddedTaxAmount().getNumber().doubleValue());
        entity.setNetRevenue(domain.getNetRevenue().getNumber().doubleValue());
        entity.setBusinessTaxAmount(domain.getBusinessTaxAmount().getNumber().doubleValue());
        entity.setBussinesTaxPrePaymentDifference(domain.getBussinesTaxPrePaymentDifference().getNumber().doubleValue());
        entity.setIncomeTaxAmount(domain.getIncomeTaxAmount().getNumber().doubleValue());
        entity.setIncomeTaxPrePaymentDifference(domain.getIncomeTaxPrePaymentDifference().getNumber().doubleValue());
        entity.setSolidarityTaxAmount(domain.getSolidarityTaxAmount().getNumber().doubleValue());
        entity.setSolidarityTaxPrePaymentDifference(domain.getSolidarityTaxPrePaymentDifference().getNumber().doubleValue());
        entity.setProfitAfterTax(domain.getProfitAfterTax().getNumber().doubleValue());
        entity.setProfitAfterTaxWithPrePaymentDifference(domain.getProfitAfterTaxWithPrePaymentDifference().getNumber().doubleValue());
        entity.setProfitAfterPrivateExpenses(domain.getProfitAfterPrivateExpenses().getNumber().doubleValue());
        entity.setProfitAfterPrivateExpensesWithPrePaymentDifference(domain.getProfitAfterPrivateExpensesWithPrePaymentDifference().getNumber().doubleValue());
        return entity;
    }
}
