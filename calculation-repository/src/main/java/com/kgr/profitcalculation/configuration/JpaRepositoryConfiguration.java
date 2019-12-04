package com.kgr.profitcalculation.configuration;

import com.kgr.profitcalculation.repository.YearlyProfitCalculationRepository;
import com.kgr.profitcalculation.repository.jpa.YearlyCalculationMapper;
import com.kgr.profitcalculation.repository.jpa.YearlyProfitCalculationJpaRepository;
import com.kgr.profitcalculation.repository.jpa.YearlyProfitCalculationJpaRepositoryAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "calculation.cache", havingValue = "jpa")
public class JpaRepositoryConfiguration {

    @Bean(name = "yearlyProfitCalculationRepository")
    YearlyProfitCalculationRepository yearlyProfitCalculationSimpleRepository(YearlyProfitCalculationJpaRepository repository, YearlyCalculationMapper mapper) {
        return new YearlyProfitCalculationJpaRepositoryAdapter(repository, mapper);
    }
}
