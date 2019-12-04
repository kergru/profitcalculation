package com.kgr.profitcalculation.repository.jpa;

import com.kgr.profitcalculation.domain.ProfitCalculator;
import com.kgr.profitcalculation.domain.tax.TaxPrePayment;
import com.kgr.profitcalculation.repository.jpa.entity.TaxPrePaymentEntity;
import org.springframework.stereotype.Component;

@Component
public class TaxPrePaymentMapper {

    TaxPrePayment toDomain(TaxPrePaymentEntity entity) {
        return new TaxPrePayment(ProfitCalculator.money(entity.getAmount()), entity.getPrePaymentInterval());
    }

    TaxPrePaymentEntity toEntity(TaxPrePayment domain) {
        TaxPrePaymentEntity entity = new TaxPrePaymentEntity();
        entity.setAmount(domain.getPrePaymentAmount().getNumber().doubleValue());
        entity.setPrePaymentInterval(domain.getTaxPrePaymentInterval());
        return entity;
    }
}
