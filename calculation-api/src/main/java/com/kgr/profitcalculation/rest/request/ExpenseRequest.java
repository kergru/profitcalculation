package com.kgr.profitcalculation.rest.request;

import com.kgr.profitcalculation.domain.ExpenseCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ExpenseRequest {

    @NotNull
    private ExpenseCategory category;
    @NotNull
    private String title;
    @NotNull
    private double amount;

    private Integer startMonth;

    private Integer endMonth;
}
