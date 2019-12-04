package com.kgr.profitcalculation.domain;

import com.kgr.profitcalculation.domain.tax.TaxPrePayment;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.money.MonetaryAmount;
import java.time.YearMonth;

import static com.kgr.profitcalculation.domain.ProfitCalculator.ZERO;
import static java.time.Month.APRIL;
import static java.time.Month.AUGUST;
import static java.time.Month.DECEMBER;
import static java.time.Month.FEBRUARY;
import static java.time.Month.JANUARY;
import static java.time.Month.JULY;
import static java.time.Month.JUNE;
import static java.time.Month.MARCH;
import static java.time.Month.MAY;
import static java.time.Month.NOVEMBER;
import static java.time.Month.OCTOBER;
import static java.time.Month.SEPTEMBER;
import static java.time.YearMonth.of;
import static java.util.Arrays.stream;
import static lombok.AccessLevel.PRIVATE;
import static org.javamoney.moneta.function.MonetaryFunctions.sum;

@NoArgsConstructor(access = PRIVATE)
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class YearlyProfitCalculation extends AbstractCalculation {

    private int year;

    private TaxPrePayment businessTaxPrePayment;

    private TaxPrePayment incomeTaxPrePayment;

    private TaxPrePayment solidarityTaxPrePayment;

    private MonthlyProfitCalculation[] monthlyProfitCalculations;

    public static YearlyProfitCalculation create(int year) {
        YearlyProfitCalculation calculation = new YearlyProfitCalculation();
        calculation.year = year;
        calculation.monthlyProfitCalculations = new MonthlyProfitCalculation[]{
                MonthlyProfitCalculation.create(of(year, JANUARY)),
                MonthlyProfitCalculation.create(of(year, FEBRUARY)),
                MonthlyProfitCalculation.create(of(year, MARCH)),
                MonthlyProfitCalculation.create(of(year, APRIL)),
                MonthlyProfitCalculation.create(of(year, MAY)),
                MonthlyProfitCalculation.create(of(year, JUNE)),
                MonthlyProfitCalculation.create(of(year, JULY)),
                MonthlyProfitCalculation.create(of(year, AUGUST)),
                MonthlyProfitCalculation.create(of(year, SEPTEMBER)),
                MonthlyProfitCalculation.create(of(year, OCTOBER)),
                MonthlyProfitCalculation.create(of(year, NOVEMBER)),
                MonthlyProfitCalculation.create(of(year, DECEMBER))
        };
        return calculation;
    }

    public MonthlyProfitCalculation getMonthlyProfitCalculation(YearMonth yearMonth) {
        return stream(monthlyProfitCalculations).filter(c -> c.getYearMonth().equals(yearMonth)).findFirst().get();
    }

    @Override
    public MonetaryAmount getSumFixedBusinessExpenses() {
        return stream(monthlyProfitCalculations)
                .map(c -> c.getFixedBusinessExpenses().stream().map(e -> e.getAmount()).reduce(sum()).orElse(ZERO))
                .reduce(sum()).orElse(ZERO);
    }

    @Override
    public MonetaryAmount getSumFixedPrivateExpenses() {
        return stream(monthlyProfitCalculations)
                .map(c -> c.getFixedPrivateExpenses().stream().map(e -> e.getAmount()).reduce(sum()).orElse(ZERO))
                .reduce(sum()).orElse(ZERO);
    }
}
