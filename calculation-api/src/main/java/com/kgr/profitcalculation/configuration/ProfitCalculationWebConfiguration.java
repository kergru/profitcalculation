package com.kgr.profitcalculation.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kgr.profitcalculation.domain.ProfitCalculator;
import com.kgr.profitcalculation.repository.YearlyProfitCalculationRepository;
import com.kgr.profitcalculation.rest.ser.SimpleMonetaryAmountSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.money.MonetaryAmount;

@Configuration
public class ProfitCalculationWebConfiguration {

    @Bean
    public ProfitCalculator profitCalculation(YearlyProfitCalculationRepository repository) {
        return new ProfitCalculator(repository);
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        ObjectMapper om = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(MonetaryAmount.class, new SimpleMonetaryAmountSerializer());
        om.registerModule(module);
        om.registerModule(new JavaTimeModule());
        om.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
        om.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return om;
    }
}
