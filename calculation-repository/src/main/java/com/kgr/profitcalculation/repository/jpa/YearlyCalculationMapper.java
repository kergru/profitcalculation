package com.kgr.profitcalculation.repository.jpa;

import com.kgr.profitcalculation.domain.YearlyProfitCalculation;
import com.kgr.profitcalculation.repository.jpa.entity.YearlyProfitCalculationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class YearlyCalculationMapper {

    private final TaxPrePaymentMapper taxPrePaymentMapper;

    private final MonthlyCalculationMapper monthlyCalculationMapper;

    private final CalculationValuesMapper calculationValuesMapper;

    YearlyProfitCalculation toDomain(YearlyProfitCalculationEntity entity) {
        YearlyProfitCalculation domain = YearlyProfitCalculation.create(entity.getYear());
        if (entity.getBusinessTaxPrePayment() != null) {
            domain.setBusinessTaxPrePayment(taxPrePaymentMapper.toDomain(entity.getBusinessTaxPrePayment()));
        }
        if (entity.getIncomeTaxPrePayment() != null) {
            domain.setIncomeTaxPrePayment(taxPrePaymentMapper.toDomain(entity.getIncomeTaxPrePayment()));
        }
        if (entity.getSolidarityTaxPrePayment() != null) {
            domain.setSolidarityTaxPrePayment(taxPrePaymentMapper.toDomain(entity.getSolidarityTaxPrePayment()));
        }
        domain.setMonthlyProfitCalculations(monthlyCalculationMapper.toDomains(entity.getMonthlyProfitCalculations()));
        calculationValuesMapper.toDomain(domain, entity.getCalculationValues());
        return domain;
    }

    YearlyProfitCalculationEntity toEntity(YearlyProfitCalculation domain) {
        YearlyProfitCalculationEntity entity = new YearlyProfitCalculationEntity();
        entity.setYear(domain.getYear());
        if (domain.getBusinessTaxPrePayment() != null) {
            entity.setBusinessTaxPrePayment(taxPrePaymentMapper.toEntity(domain.getBusinessTaxPrePayment()));
        }
        if (domain.getIncomeTaxPrePayment() != null) {
            entity.setIncomeTaxPrePayment(taxPrePaymentMapper.toEntity(domain.getIncomeTaxPrePayment()));
        }
        if (domain.getSolidarityTaxPrePayment() != null) {
            entity.setSolidarityTaxPrePayment(taxPrePaymentMapper.toEntity(domain.getSolidarityTaxPrePayment()));
        }
        entity.setMonthlyProfitCalculations(monthlyCalculationMapper.toEntities(domain.getMonthlyProfitCalculations()));
        entity.setCalculationValues(calculationValuesMapper.toEntity(domain));
        return entity;
    }
}
