package com.kgr.profitcalculation.configuration;

import com.kgr.profitcalculation.repository.YearlyProfitCalculationRepository;
import com.kgr.profitcalculation.repository.simple.YearlyProfitCalculationSimpleRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "calculation.cache", havingValue = "simple")
public class SimpleRepositoryConfiguration {

    @Bean(name = "yearlyProfitCalculationRepository")
    YearlyProfitCalculationRepository yearlyProfitCalculationSimpleRepository() {
        return new YearlyProfitCalculationSimpleRepository();
    }
}
