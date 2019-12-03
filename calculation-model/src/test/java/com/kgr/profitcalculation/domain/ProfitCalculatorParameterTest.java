package com.kgr.profitcalculation.domain;

import org.junit.jupiter.api.Test;

import static com.kgr.profitcalculation.domain.MonthlyWork.noProject;
import static com.kgr.profitcalculation.domain.TestdataFactory.CALCULATION_PARAMETER_100h_NO_PREPAYMENT;
import static com.kgr.profitcalculation.domain.TestdataFactory.MONTHLY_WORK_100h;
import static java.time.Month.FEBRUARY;
import static java.time.Month.JANUARY;
import static java.time.Month.MARCH;
import static org.assertj.core.api.Assertions.assertThat;

class ProfitCalculatorParameterTest {

    @Test
    void get_monthly_work_should_return_work() {

        MonthlyWork monthlyWork = CALCULATION_PARAMETER_100h_NO_PREPAYMENT.getMonthlyWork(JANUARY);

        assertThat(monthlyWork).isEqualTo(MONTHLY_WORK_100h);
    }

    @Test
    void get_monthly_work_should_return_zerowork_if_month_not_set() {

        MonthlyWork monthlyWork = CALCULATION_PARAMETER_100h_NO_PREPAYMENT.getMonthlyWork(FEBRUARY);

        assertThat(monthlyWork).isEqualTo(noProject(FEBRUARY));
    }

    @Test
    void get_monthly_work_should_return_zerowork_if_monthlyworks_not_set() {

        MonthlyWork monthlyWork = CALCULATION_PARAMETER_100h_NO_PREPAYMENT.getMonthlyWork(MARCH);

        assertThat(monthlyWork).isEqualTo(noProject(MARCH));
    }
}