package com.kgr.profitcalculation.console.cli;

import lombok.RequiredArgsConstructor;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.CellMatchers;
import org.springframework.shell.table.SimpleHorizontalAligner;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;

import javax.money.MonetaryAmount;

@RequiredArgsConstructor
public class ConsolePrinter {

    private final Terminal terminal;

    private MoneyFormatter moneyFormatter = new MoneyFormatter();

    public void print(String message) {
        terminal.writer().println(message);
        terminal.flush();
    }

    public void printError(String message) {
        String printMessage = new AttributedStringBuilder().append(message, AttributedStyle.DEFAULT.foreground(1)).toAnsi();
        terminal.writer().println(printMessage);
        terminal.flush();
    }

    public void printModel(TableModel tableModel) {
        TableBuilder tableBuilder = new TableBuilder(tableModel);
        tableBuilder.on(CellMatchers.ofType(MonetaryAmount.class))
                .addFormatter(moneyFormatter)
                .addAligner(SimpleHorizontalAligner.right);
        tableBuilder.addFullBorder(BorderStyle.oldschool);
        print(tableBuilder.build().render(100));
    }
}
