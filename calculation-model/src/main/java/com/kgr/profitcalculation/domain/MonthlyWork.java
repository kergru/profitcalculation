package com.kgr.profitcalculation.domain;

import lombok.Value;

import javax.money.MonetaryAmount;
import java.time.Month;

@Value
public class MonthlyWork {

    private final Month month;

    private final MonetaryAmount ratePerHour;

    private final double hoursWorked;

    public static MonthlyWork noProject(Month month) {
        return new MonthlyWork(month, null, 0);
    }

    public void validate() {
        if (month == null) {
            throw new IllegalArgumentException("month missing");
        } else if (hoursWorked > 0 && ratePerHour == null) {
            throw new IllegalArgumentException("rate missing");
        }
    }
}
