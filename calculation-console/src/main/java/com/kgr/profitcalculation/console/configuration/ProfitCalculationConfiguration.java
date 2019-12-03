package com.kgr.profitcalculation.console.configuration;

import com.kgr.profitcalculation.console.adapter.ProfitCalculationConsoleAdapter;
import com.kgr.profitcalculation.console.cli.ConsolePrinter;
import com.kgr.profitcalculation.console.cli.InputReader;
import com.kgr.profitcalculation.domain.ProfitCalculator;
import com.kgr.profitcalculation.repository.YearlyProfitCalculationRepository;
import org.jline.reader.History;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.Parser;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedString;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.jline.JLineShellAutoConfiguration;
import org.springframework.shell.jline.PromptProvider;

@Configuration
public class ProfitCalculationConfiguration {

    @Bean
    public ProfitCalculator profitCalculation(YearlyProfitCalculationRepository repository) {
        return new ProfitCalculator(repository);
    }

    @Bean
    public PromptProvider profitCalculationPrompt() {
        return () -> new AttributedString("Profitcalculation: type 'start' or 'show {yyyy}'>");
    }

    @Bean
    public ProfitCalculationConsoleAdapter profitCalculationConsoleAdapter(YearlyProfitCalculationRepository repository) {
        return new ProfitCalculationConsoleAdapter(profitCalculation(repository));
    }

    @Bean
    public ConsolePrinter printer(@Lazy Terminal terminal) {
        return new ConsolePrinter(terminal);
    }

    @Bean
    public InputReader inputReader(
            @Lazy Terminal terminal,
            @Lazy Parser parser,
            JLineShellAutoConfiguration.CompleterAdapter completer,
            @Lazy History history
    ) {
        LineReaderBuilder lineReaderBuilder = LineReaderBuilder.builder()
                .terminal(terminal)
                .completer(completer)
                .history(history)
                .parser(parser);

        LineReader lineReader = lineReaderBuilder.build();
        lineReader.unsetOpt(LineReader.Option.INSERT_TAB);
        return new InputReader(lineReader);
    }
}
