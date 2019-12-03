package com.kgr.profitcalculation.console.cli;

import com.kgr.profitcalculation.console.adapter.ProfitCalculationConsoleAdapter;
import com.kgr.profitcalculation.console.adapter.ProfitCalculationModelConverter;
import com.kgr.profitcalculation.domain.Expense;
import com.kgr.profitcalculation.domain.ExpenseCategory;
import com.kgr.profitcalculation.domain.MonthlyWork;
import com.kgr.profitcalculation.domain.YearlyProfitCalculation;
import com.kgr.profitcalculation.domain.tax.TaxPrePayment;
import com.kgr.profitcalculation.domain.tax.TaxPrePaymentInterval;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.TableModel;

import javax.money.MonetaryAmount;
import java.time.Month;
import java.time.Year;

import static com.kgr.profitcalculation.domain.ProfitCalculator.money;
import static com.kgr.profitcalculation.domain.tax.TaxPrePayment.ZERO_PRE_PAYMENT;
import static com.kgr.profitcalculation.domain.tax.TaxPrePaymentInterval.MONTHLY;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.split;

@ShellComponent
public class ProfitCalculationCli {

    @Autowired
    private ConsolePrinter printer;

    @Autowired
    private InputReader inputReader;

    @Autowired
    private ProfitCalculationConsoleAdapter adapter;

    private ProfitCalculationModelConverter modelConverter = new ProfitCalculationModelConverter();

    private ProfitCalculationInput calculationInput;

    @ShellMethod("show calculation")
    public void show(int year) {
        YearlyProfitCalculation calculation = adapter.getCalculation(year);
        showCalculation(calculation);
    }

    @ShellMethod("start calculation")
    public void start() {
        calculationInput = new ProfitCalculationInput();
        setYear();
        setBusinessTaxPrePayment();
        setIncomeTaxPrePayment();
        setSolidarityTaxPrePayment();
        setBusinessExpenses(calculationInput.getYear());
        setPrivateExpenses(calculationInput.getYear());
        setMonthlyWorks();

        showCalculation(adapter.calculate(calculationInput));
    }

    private void showCalculation(YearlyProfitCalculation yearlyProfitCalculation) {
        TableModel model = modelConverter.convert(yearlyProfitCalculation);
        printer.printModel(model);
    }

    private void setYear() {
        do {
            String year = inputReader.prompt("Year");
            try {
                calculationInput.setYear(parseYear(year));
            } catch (Exception ex) {
                printer.printError(ex.getMessage());
            }
        } while (calculationInput.getYear() == null);
    }

    private Year parseYear(String year) {
        Year retVal = null;
        try {
            retVal = Year.of(parseInt(year));
        } catch (NumberFormatException e) {
            throw new ParseInputException("Invalid format, required format: yyyy");
        }
        if (retVal.isBefore(Year.of(2000))) {
            throw new ParseInputException("Min year: 2000");
        }
        return retVal;
    }

    private void setBusinessTaxPrePayment() {
        do {
            String businesstaxPrepayment = inputReader.prompt("Businesstax prepayment");
            try {
                TaxPrePayment taxPrePayment = parseTaxPrepayment(businesstaxPrepayment);
                calculationInput.setBusinessTaxPrePayment(taxPrePayment);
            } catch (Exception ex) {
                printer.printError(ex.getMessage());
            }
        } while (calculationInput.getBusinessTaxPrePayment() == null);
    }

    private void setIncomeTaxPrePayment() {
        do {
            String incometaxPrepayment = inputReader.prompt("Incometax prepayment");
            try {
                TaxPrePayment taxPrePayment = parseTaxPrepayment(incometaxPrepayment);
                calculationInput.setIncomeTaxPrePayment(taxPrePayment);
            } catch (Exception ex) {
                printer.printError(ex.getMessage());
            }
        } while (calculationInput.getIncomeTaxPrePayment() == null);
    }

    private void setSolidarityTaxPrePayment() {
        do {
            String solidaritytaxPrepayment = inputReader.prompt("Solidaritytax prepayment");
            try {
                TaxPrePayment taxPrePayment = parseTaxPrepayment(solidaritytaxPrepayment);
                calculationInput.setSolidarityTaxPrePayment(taxPrePayment);
            } catch (Exception ex) {
                printer.printError(ex.getMessage());
            }
        } while (calculationInput.getSolidarityTaxPrePayment() == null);
    }

    private void setBusinessExpenses(Year year) {
        setExpenses(year, "Business expenses", ExpenseCategory.BUSINESS);
    }

    private void setPrivateExpenses(Year year) {
        setExpenses(year, "Private expenses", ExpenseCategory.PRIVATE);
    }

    private void setExpenses(Year year, String title, ExpenseCategory category) {
        boolean addExpense = true;
        do {
            String expense = inputReader.prompt(title);
            if (isNotBlank(expense)) {
                try {
                    calculationInput.addExpense(parseExpense(year.getValue(), expense, category));
                } catch (Exception ex) {
                    printer.printError(ex.getMessage());
                }
            } else {
                addExpense = false;
            }
        } while (addExpense);
    }

    private void setMonthlyWorks() {
        Month[] months = Month.values();
        for (int i = 0; i < months.length; ) {
            Month month = months[i];
            do {
                String work = inputReader.prompt("Work in " + month);
                try {
                    MonthlyWork monthlyWork = parseMonthlyWork(work, month);
                    calculationInput.addMonthlyWork(monthlyWork);
                    i++;
                } catch (Exception ex) {
                    printer.printError(ex.getMessage());
                }
            } while (!calculationInput.hasMonthlyWork(month));
        }
    }

    private MonthlyWork parseMonthlyWork(String work, Month month) {
        MonthlyWork monthlyWork = MonthlyWork.noProject(month);
        if (isNotBlank(work)) {
            String[] split = split(work);
            if (split.length != 2) {
                throw new ParseInputException("Invalid input, required input: {rate/hour} {hours}");
            }
            monthlyWork = new MonthlyWork(month, parseMoney(split[0]), parseDouble(split[1]));
        }
        return monthlyWork;
    }

    private MonetaryAmount parseMoney(String value) {
        try {
            return money(parseDouble(value));
        } catch (Exception e) {
            throw new ParseInputException("Invalid format for money");
        }
    }

    private Expense parseExpense(int year, String expense, ExpenseCategory category) {
        String[] split = split(expense);
        if (split.length < 2) {
            throw new ParseInputException("Invalid input, required input: {title} {amount} {?start} {?end}");
        }
        Month start = null;
        if (split.length >= 3) {
            start = Month.of(parseInt(split[2]));
        }
        Month end = null;
        if (split.length == 4) {
            end = Month.of(parseInt(split[3]));
        }
        return new Expense(category, split[0], parseMoney(split[1]), start, end);
    }

    private TaxPrePayment parseTaxPrepayment(String prepayment) {
        TaxPrePayment taxPrePayment = ZERO_PRE_PAYMENT;
        if (isNotBlank(prepayment)) {
            TaxPrePaymentInterval interval = MONTHLY;
            String[] split = split(prepayment);
            if (split.length == 2) {
                interval = TaxPrePaymentInterval.valueOf(StringUtils.upperCase(split[1]));
            }
            taxPrePayment = new TaxPrePayment(parseMoney(split[0]), interval);
        }
        return taxPrePayment;
    }
}
