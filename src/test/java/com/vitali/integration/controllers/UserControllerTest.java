package com.vitali.integration.controllers;

import com.vitali.integration.annotation.IT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@IT
@RequiredArgsConstructor
class UserControllerTest {
    private final MockMvc mockMvc;

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/registration/add")
                        .param("username", "Jimmy")
                        .param("email", "jimmy@gmail.com")
                        .param("rawPassword", "123")
                        .param("matchingPassword", "123")
                        .param("role", "USER")
                        .param("enabled", "true")
                        .param("cartId", "1")
                        .param("firstName", "Jimmy")
                        .param("lastName", "Diny")
                        .param("phone", "+37534534534")
                        .param("address", "Minsk")
                        .param("birthDate", "1995-10-05")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/login")
                );
    }
}