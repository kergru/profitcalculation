package com.kgr.profitcalculation.rest;

import com.kgr.profitcalculation.domain.ProfitCalculationCommand;
import com.kgr.profitcalculation.domain.ProfitCalculator;
import com.kgr.profitcalculation.domain.YearlyProfitCalculation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.kgr.profitcalculation.rest.TestDataFactory.aProfitCalculationCommand;
import static com.kgr.profitcalculation.rest.TestDataFactory.aProfitCalculationRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ProfitCalculationResourceTest {

    private MockMvc mockMvc;

    @Mock
    private ProfitCalculator profitCalculator;

    @BeforeEach
    void init() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new ProfitCalculationResource(profitCalculator)).build();
    }

    @Test
    void get_should_return_calculation() throws Exception {
        YearlyProfitCalculation calculation = YearlyProfitCalculation.create(2019);
        doReturn(calculation).when(profitCalculator).getCalculation(2019);

        mockMvc.perform(get("/api/calculation/2019"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.year").value(2019));
    }

    @Test
    void post_should_calculate_with_parameters() throws Exception {
        YearlyProfitCalculation calculation = YearlyProfitCalculation.create(2019);
        ArgumentCaptor<ProfitCalculationCommand> captor = ArgumentCaptor.forClass(ProfitCalculationCommand.class);
        doReturn(calculation).when(profitCalculator).calculate(eq(2019), captor.capture());

        mockMvc.perform(post("/api/calculation/2019")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(aProfitCalculationRequest()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.year").value(2019));

        assertThat(captor.getValue()).isEqualTo(aProfitCalculationCommand());
    }
}