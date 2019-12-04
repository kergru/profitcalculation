package com.kgr.profitcalculation.repository.jpa;

import com.kgr.profitcalculation.domain.FixedMonthlyExpense;
import com.kgr.profitcalculation.repository.jpa.entity.ExpenseType;
import com.kgr.profitcalculation.repository.jpa.entity.FixedMonthlyExpenseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.kgr.profitcalculation.domain.ProfitCalculator.money;
import static java.util.stream.Collectors.toList;

@Component
public class FixedMonthlyExpenseMapper {

    List<FixedMonthlyExpense> toDomains(List<FixedMonthlyExpenseEntity> entities) {
        return entities.stream().map(entity -> toDomain(entity)).collect(toList());
    }

    FixedMonthlyExpense toDomain(FixedMonthlyExpenseEntity entity) {
        return new FixedMonthlyExpense(entity.getTitle(), money(entity.getAmount()));
    }

    List<FixedMonthlyExpenseEntity> toEntities(List<FixedMonthlyExpense> domains, ExpenseType type) {
        return domains.stream().map(domain -> toEntity(domain, type)).collect(toList());
    }

    FixedMonthlyExpenseEntity toEntity(FixedMonthlyExpense domain, ExpenseType type) {
        FixedMonthlyExpenseEntity entity = new FixedMonthlyExpenseEntity();
        entity.setTitle(domain.getTitle());
        entity.setAmount(domain.getAmount().getNumber().doubleValue());
        entity.setExpenseType(type);
        return entity;
    }
}
