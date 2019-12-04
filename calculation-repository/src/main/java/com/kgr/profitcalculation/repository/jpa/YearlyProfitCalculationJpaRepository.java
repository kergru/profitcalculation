package com.kgr.profitcalculation.repository.jpa;

import com.kgr.profitcalculation.repository.jpa.entity.YearlyProfitCalculationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface YearlyProfitCalculationJpaRepository extends JpaRepository<YearlyProfitCalculationEntity, Long> {

    Optional<YearlyProfitCalculationEntity> findByYear(int year);
}
