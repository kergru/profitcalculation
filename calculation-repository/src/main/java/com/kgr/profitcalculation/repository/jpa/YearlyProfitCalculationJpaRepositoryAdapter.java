package com.kgr.profitcalculation.repository.jpa;

import com.kgr.profitcalculation.domain.YearlyProfitCalculation;
import com.kgr.profitcalculation.repository.YearlyProfitCalculationRepository;
import com.kgr.profitcalculation.repository.jpa.entity.YearlyProfitCalculationEntity;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class YearlyProfitCalculationJpaRepositoryAdapter implements YearlyProfitCalculationRepository {

    private final YearlyProfitCalculationJpaRepository repository;

    private final YearlyCalculationMapper yearlyCalculationMapper;

    @Override
    public YearlyProfitCalculation findByYear(int year) {
        YearlyProfitCalculation retVal = null;
        Optional<YearlyProfitCalculationEntity> entity = repository.findByYear(year);
        if (entity.isPresent()) {
            retVal = mapToDomain(entity.get());
        }
        return retVal;
    }

    private YearlyProfitCalculation mapToDomain(YearlyProfitCalculationEntity entity) {
        return yearlyCalculationMapper.toDomain(entity);
    }

    @Override
    public void save(YearlyProfitCalculation yearlyCalculation) {
        repository.save(mapToEntity(yearlyCalculation));
    }

    private YearlyProfitCalculationEntity mapToEntity(YearlyProfitCalculation yearlyCalculation) {
        return yearlyCalculationMapper.toEntity(yearlyCalculation);
    }
}
