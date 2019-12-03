package com.kgr.profitcalculation.repository.simple;

import com.kgr.profitcalculation.domain.YearlyProfitCalculation;
import com.kgr.profitcalculation.repository.YearlyProfitCalculationRepository;

import java.util.HashMap;
import java.util.Map;

public class YearlyProfitCalculationSimpleRepository implements YearlyProfitCalculationRepository {

    Map<Integer, YearlyProfitCalculation> cache = new HashMap<>();

    @Override
    public YearlyProfitCalculation findByYear(int year) {
        return cache.get(year);
    }

    @Override
    public void save(YearlyProfitCalculation yearlyCalculation) {
        cache.put(yearlyCalculation.getYear(), yearlyCalculation);
    }

}
