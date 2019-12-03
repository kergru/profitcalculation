package com.kgr.profitcalculation.domain;

import lombok.Value;

import javax.money.MonetaryAmount;


@Value
public class FixedMonthlyExpense {

    private final String title;

    private final MonetaryAmount amount;

    static FixedMonthlyExpense from(Expense expense) {
        return new FixedMonthlyExpense(expense.getTitle(), expense.getAmount());
    }
}
