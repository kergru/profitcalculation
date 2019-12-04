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
@Table(name = "yearly_calculation")
@Getter
@Setter
public class YearlyProfitCalculationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int year;

    @OneToOne(cascade = {CascadeType.ALL})
    private TaxPrePaymentEntity businessTaxPrePayment;

    @OneToOne(cascade = {CascadeType.ALL})
    private TaxPrePaymentEntity incomeTaxPrePayment;

    @OneToOne(cascade = {CascadeType.ALL})
    private TaxPrePaymentEntity solidarityTaxPrePayment;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MonthlyProfitCalculationEntity> monthlyProfitCalculations;

    @OneToOne(cascade = {CascadeType.ALL})
    private CalculationValuesEntity calculationValues;
}
