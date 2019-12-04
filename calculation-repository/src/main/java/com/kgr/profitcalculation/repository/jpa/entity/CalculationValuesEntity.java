package com.kgr.profitcalculation.repository.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "calculation_values")
@Getter
@Setter
public class CalculationValuesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double hoursWorked;

    private double grossPay;

    private double grossRevenue;

    private double valueAddedTaxAmount;

    private double netRevenue;

    private double businessTaxAmount;

    private double bussinesTaxPrePaymentDifference;

    private double incomeTaxAmount;

    private double incomeTaxPrePaymentDifference;

    private double solidarityTaxAmount;

    private double solidarityTaxPrePaymentDifference;

    private double profitAfterTax;

    private double profitAfterTaxWithPrePaymentDifference;

    private double profitAfterPrivateExpenses;

    private double profitAfterPrivateExpensesWithPrePaymentDifference;
}
