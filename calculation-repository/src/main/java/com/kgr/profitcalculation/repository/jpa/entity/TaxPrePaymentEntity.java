package com.kgr.profitcalculation.repository.jpa.entity;

import com.kgr.profitcalculation.domain.tax.TaxPrePaymentInterval;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tax_prepayment")
@Getter
@Setter
public class TaxPrePaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double amount;

    private TaxPrePaymentInterval prePaymentInterval;
}


