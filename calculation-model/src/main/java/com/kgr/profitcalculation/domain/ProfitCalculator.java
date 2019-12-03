package com.kgr.profitcalculation.domain;

import com.kgr.profitcalculation.repository.YearlyProfitCalculationRepository;
import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;

@RequiredArgsConstructor
public class ProfitCalculator {

    public static final CurrencyUnit EURO = Monetary.getCurrency("EUR");

    public static final MonetaryAmount ZERO = Money.zero(EURO);

    public static MonetaryAmount money(Number number) {
        return Money.of(number, EURO);
    }

    private final YearlyProfitCalculationRepository yearlyProfitCalculationRepository;

    public YearlyProfitCalculation getCalculation(int year) {
        YearlyProfitCalculation yearlyCalculation = findOrCreate(year);
        return yearlyCalculation;
    }

    public YearlyProfitCalculation calculate(int year, ProfitCalculationCommand calculationParameter) {
        YearlyProfitCalculation yearlyCalculation = findOrCreate(year);

        if (calculationParameter != null) {
            calculationParameter.validate();
            new YearlyProfitCalculator(yearlyCalculation).calculate(calculationParameter);
        }

        persist(yearlyCalculation);

        return yearlyCalculation;
    }

    private YearlyProfitCalculation findOrCreate(int year) {
        YearlyProfitCalculation yearlyCalculation = findYearlyProfitCalculation(year);
        if (yearlyCalculation == null) {
            yearlyCalculation = YearlyProfitCalculation.create(year);
        }
        return yearlyCalculation;
    }

    private void persist(YearlyProfitCalculation yearlyCalculation) {
        yearlyProfitCalculationRepository.save(yearlyCalculation);
    }

    private YearlyProfitCalculation findYearlyProfitCalculation(int year) {
        return yearlyProfitCalculationRepository.findByYear(year);
    }
}
