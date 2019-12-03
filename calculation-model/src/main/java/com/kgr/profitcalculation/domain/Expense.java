package com.kgr.profitcalculation.domain;

import lombok.Value;

import javax.money.MonetaryAmount;
import java.time.Month;

import static com.kgr.profitcalculation.domain.ExpenseCategory.BUSINESS;

@Value
public class Expense {

    private ExpenseCategory category;

    private String title;

    private MonetaryAmount amount;

    private Month startMonth;

    private Month endMonth;

    public boolean isBusinessExpense() {
        return category == BUSINESS;
    }

    public void validate() {
        if (category == null) {
            throw new IllegalArgumentException("category missing");
        } else if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("expense title missing");
        } else if (amount == null) {
            throw new IllegalArgumentException("amount missing");
        } else if (startMonth != null && endMonth != null && startMonth.getValue() > endMonth.getValue()) {
            throw new IllegalArgumentException("invalid date range");
        }

    }
}
