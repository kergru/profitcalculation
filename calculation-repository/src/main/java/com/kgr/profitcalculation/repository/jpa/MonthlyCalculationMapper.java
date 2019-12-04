package com.kgr.profitcalculation.repository.jpa;

import com.kgr.profitcalculation.domain.MonthlyProfitCalculation;
import com.kgr.profitcalculation.repository.jpa.entity.MonthlyProfitCalculationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.YearMonth;
import java.util.List;

import static com.kgr.profitcalculation.domain.ProfitCalculator.money;
import static com.kgr.profitcalculation.repository.jpa.entity.ExpenseType.BUSINESS;
import static com.kgr.profitcalculation.repository.jpa.entity.ExpenseType.PRIVATE;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class MonthlyCalculationMapper {

    private final FixedMonthlyExpenseMapper expenseMapper;

    private final CalculationValuesMapper calculationValuesMapper;

    MonthlyProfitCalculation[] toDomains(List<MonthlyProfitCalculationEntity> monthlyProfitCalculations) {
        return monthlyProfitCalculations.stream().map(entity -> toDomain(entity)).toArray(MonthlyProfitCalculation[]::new);
    }

    MonthlyProfitCalculation toDomain(MonthlyProfitCalculationEntity entity) {
        YearMonth yearMonth = YearMonth.of(entity.getYear(), entity.getMonth());
        MonthlyProfitCalculation domain = MonthlyProfitCalculation.create(yearMonth);
        if (entity.getRatePerHour() != null) {
            domain.setRatePerHour(money(entity.getRatePerHour()));
        }
        domain.setFixedBusinessExpenses(expenseMapper.toDomains(entity.getBusinessExpenses()));
        domain.setFixedPrivateExpenses(expenseMapper.toDomains(entity.getPrivateExpenses()));
        calculationValuesMapper.toDomain(domain, entity.getCalculationValues());
        return domain;
    }

    public List<MonthlyProfitCalculationEntity> toEntities(MonthlyProfitCalculation[] monthlyProfitCalculations) {
        return stream(monthlyProfitCalculations).map(domain -> toEntity(domain)).collect(toList());
    }

    private MonthlyProfitCalculationEntity toEntity(MonthlyProfitCalculation domain) {
        MonthlyProfitCalculationEntity entity = new MonthlyProfitCalculationEntity();
        entity.setYear(domain.getYearMonth().getYear());
        entity.setMonth(domain.getYearMonth().getMonthValue());
        if (domain.getRatePerHour() != null) {
            entity.setRatePerHour(domain.getRatePerHour().getNumber().doubleValue());
        }
        entity.setBusinessExpenses(expenseMapper.toEntities(domain.getFixedBusinessExpenses(), BUSINESS));
        entity.setPrivateExpenses(expenseMapper.toEntities(domain.getFixedPrivateExpenses(), PRIVATE));
        entity.setCalculationValues(calculationValuesMapper.toEntity(domain));
        return entity;
    }
}
