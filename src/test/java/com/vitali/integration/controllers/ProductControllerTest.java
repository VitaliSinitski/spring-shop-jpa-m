package com.vitali.integration.controllers;

import com.vitali.integration.annotation.IT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@AutoConfigureMockMvc
@IT
@RequiredArgsConstructor
class ProductControllerTest {
    private final MockMvc mockMvc;
    @Test
    void findAllProducts() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("products"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attributeExists("producers"));
    }
}