package com.kgr.profitcalculation.repository.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "monthly_expense")
@Getter
@Setter
public class FixedMonthlyExpenseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private ExpenseType expenseType;

    private String title;

    private double amount;
}
