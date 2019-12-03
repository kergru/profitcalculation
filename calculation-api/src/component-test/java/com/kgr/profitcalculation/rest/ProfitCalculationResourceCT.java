package com.kgr.profitcalculation.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static com.kgr.profitcalculation.rest.ComponentTestDataFactory.aProfitCalculationRequest;
import static com.kgr.profitcalculation.rest.ComponentTestDataFactory.anErrorResponse;
import static com.kgr.profitcalculation.rest.ComponentTestDataFactory.anInvalidProfitCalculationRequest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class ProfitCalculationResourceCT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void get_should_return_calculation() throws Exception {

        mockMvc.perform(get("/api/calculation/2018"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.year").value(2018));
    }

    @Test
    void post_should_calculate() throws Exception {

        mockMvc.perform(post("/api/calculation/2019")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(aProfitCalculationRequest()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.year").value(2019));
    }

    @Test
    void post_fail_with_404_with_invalid_parameters() throws Exception {

        mockMvc.perform(post("/api/calculation/2019")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(anInvalidProfitCalculationRequest()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(anErrorResponse()));
    }
}