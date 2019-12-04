package com.kgr.profitcalculation.repository.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "monthly_calculation")
@Getter
@Setter
public class MonthlyProfitCalculationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int year;

    private int month;

    private Double ratePerHour;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<FixedMonthlyExpenseEntity> businessExpenses;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<FixedMonthlyExpenseEntity> privateExpenses;

    @OneToOne(cascade = {CascadeType.ALL})
    private CalculationValuesEntity calculationValues;
}
