package com.kgr.profitcalculation.rest.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.Month;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MonthlyWorkRequest {

    @NotNull
    private Month month;
    @NotNull
    private double ratePerHour;
    @NotNull
    private double hoursWorked;
}
