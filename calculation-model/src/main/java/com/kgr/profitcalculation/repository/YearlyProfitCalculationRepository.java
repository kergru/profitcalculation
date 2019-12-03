package com.kgr.profitcalculation.repository;

import com.kgr.profitcalculation.domain.YearlyProfitCalculation;

public interface YearlyProfitCalculationRepository {

    YearlyProfitCalculation findByYear(int year);

    void save(YearlyProfitCalculation yearlyCalculation);
}
