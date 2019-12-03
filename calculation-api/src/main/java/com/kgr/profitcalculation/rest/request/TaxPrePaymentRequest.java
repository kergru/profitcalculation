package com.kgr.profitcalculation.rest.request;

import com.kgr.profitcalculation.domain.tax.TaxPrePaymentInterval;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TaxPrePaymentRequest {

    @NotNull
    private double amount;

    @NotNull
    private TaxPrePaymentInterval interval;
}
