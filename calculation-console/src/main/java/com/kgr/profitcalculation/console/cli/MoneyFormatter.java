package com.kgr.profitcalculation.console.cli;

import org.javamoney.moneta.format.CurrencyStyle;
import org.springframework.shell.table.Formatter;

import javax.money.MonetaryAmount;
import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.Locale;

public class MoneyFormatter implements Formatter {

    public static MonetaryAmountFormat MONEY_FORMAT = MonetaryFormats.getAmountFormat(AmountFormatQueryBuilder.
            of(Locale.GERMANY).set(CurrencyStyle.SYMBOL).set("pattern", "#,##0.00###¤").build());

    @Override
    public String[] format(Object value) {
        return new String[]{MONEY_FORMAT.format((MonetaryAmount) value)};
    }
}
