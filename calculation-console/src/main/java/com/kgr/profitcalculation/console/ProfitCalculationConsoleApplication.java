package com.kgr.profitcalculation.console;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.kgr.profitcalculation")
public class ProfitCalculationConsoleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProfitCalculationConsoleApplication.class, args);
    }

}
