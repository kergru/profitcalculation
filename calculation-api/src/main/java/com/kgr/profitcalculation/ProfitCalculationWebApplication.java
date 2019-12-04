package com.kgr.profitcalculation;

import com.kgr.profitcalculation.configuration.ProfitCalculationWebConfiguration;
import com.kgr.profitcalculation.configuration.RedisRepositoryConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({RedisRepositoryConfiguration.class, ProfitCalculationWebConfiguration.class})
public class ProfitCalculationWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProfitCalculationWebApplication.class, args);
    }

}