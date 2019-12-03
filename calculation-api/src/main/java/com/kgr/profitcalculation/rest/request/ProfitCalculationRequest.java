package com.kgr.profitcalculation.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class ProfitCalculationRequest {

    @Valid
    private TaxPrePaymentRequest businessTaxPrePayment;
    @Valid
    private TaxPrePaymentRequest incomeTaxPrePayment;
    @Valid
    private TaxPrePaymentRequest solidarityTaxPrePayment;
    @Valid
    private Collection<MonthlyWorkRequest> monthlyWorks;
    @Valid
    private List<ExpenseRequest> fixedExpenses;
}
