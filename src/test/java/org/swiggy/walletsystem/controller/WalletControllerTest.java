package org.swiggy.walletsystem.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.swiggy.walletsystem.dto.WalletDto;
import org.swiggy.walletsystem.models.entites.Wallet;
import org.swiggy.walletsystem.service.WalletServiceInterface;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WalletControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void test_addAmount() throws Exception {
        WalletDto walletDto = new WalletDto();
        walletDto.setAmount(BigDecimal.valueOf(100));
        String requestBody = objectMapper.writeValueAsString(walletDto); //or String amount = "{\"amount\":100}";
        this.mockMvc.perform(put("/wallet/1/addAmount")
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void test_deductAmount() throws Exception {
        WalletDto walletDto = new WalletDto();
        walletDto.setAmount(BigDecimal.valueOf(100));
        String requestBody = objectMapper.writeValueAsString(walletDto);
        this.mockMvc.perform(put("/wallet/1/deductAmount")
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isOk());
    }
    @Test
    void createWallet() throws Exception {
        mockMvc.perform(post("/wallet/createWallet")).andExpect(status().isCreated());
    }

    @Test
    void getAmount() {
    }

    @Test
    void deleteWallet() {
    }

    @Test
    void getWallet() {
    }
}