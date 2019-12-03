package com.kgr.profitcalculation.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.javamoney.moneta.function.MonetaryFunctions;

import javax.money.MonetaryAmount;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static com.kgr.profitcalculation.domain.ProfitCalculator.ZERO;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MonthlyProfitCalculation extends AbstractCalculation {

    private YearMonth yearMonth;

    private MonetaryAmount ratePerHour;

    private List<FixedMonthlyExpense> fixedBusinessExpenses = new ArrayList<>();

    private List<FixedMonthlyExpense> fixedPrivateExpenses = new ArrayList<>();

    static MonthlyProfitCalculation create(YearMonth yearMonth) {
        MonthlyProfitCalculation calculation = new MonthlyProfitCalculation();
        calculation.yearMonth = yearMonth;
        return calculation;
    }

    public MonetaryAmount getSumFixedBusinessExpenses() {
        return fixedBusinessExpenses != null ? fixedBusinessExpenses.stream().map(e -> e.getAmount()).reduce(MonetaryFunctions.sum()).orElse(ZERO) : ZERO;
    }

    public MonetaryAmount getSumFixedPrivateExpenses() {
        return fixedPrivateExpenses != null ? fixedPrivateExpenses.stream().map(e -> e.getAmount()).reduce(MonetaryFunctions.sum()).orElse(ZERO) : ZERO;
    }
}
