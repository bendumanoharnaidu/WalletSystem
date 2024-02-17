package org.swiggy.walletsystem.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.swiggy.walletsystem.dto.response.WalletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.swiggy.walletsystem.models.entites.Money;
import org.swiggy.walletsystem.models.entites.Wallet;
import org.swiggy.walletsystem.models.enums.Currency;
import org.swiggy.walletsystem.service.WalletServiceInterface;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WalletControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Mock
    private WalletServiceInterface walletServiceInterface;

    @InjectMocks
    private WalletController walletController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(walletController).build();
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void test_addAmount() throws Exception {
        WalletResponse walletResponse = new WalletResponse();
        walletResponse.setAmount(BigDecimal.valueOf(100));
        walletResponse.setCurrency(Currency.INR);
        String requestBody = objectMapper.writeValueAsString(walletResponse); //or String amount = "{\"amount\":100}";

        this.mockMvc.perform(put("/wallet/1/addAmount")
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isOk());
        verify(walletServiceInterface, times(1)).addAmount(1, BigDecimal.valueOf(100), Currency.INR);
//        verify(walletServiceInterface, times(1)).addAmount(1, BigDecimal.valueOf(100), Currency.INR);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void test_deductAmount() throws Exception {
        WalletResponse walletResponse = new WalletResponse();
        walletResponse.setAmount(BigDecimal.valueOf(100));
        String requestBody = objectMapper.writeValueAsString(walletResponse);
        this.mockMvc.perform(put("/wallet/1/deductAmount")
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "user", roles = "USER")
    void createWallet() throws Exception {
        mockMvc.perform(post("/wallet/createWallet")).andExpect(status().isCreated());
    }
    @Test
    @WithMockUser(username = "user", roles = "USER")
    void getAllWallets() throws Exception {
        Wallet wallet = new Wallet(1L,new Money(new BigDecimal("100"), Currency.INR));
        Wallet anotherWallet = new Wallet(2L,new Money(new BigDecimal("200"), Currency.INR));

        List<Wallet> wallets = Arrays.asList(wallet, anotherWallet);
        List<WalletResponse> walletResponses = new ArrayList<>();
        for (Wallet w : wallets) {
            WalletResponse walletResponse = new WalletResponse();
            walletResponse.setAmount(w.getMoney().getAmount());
            walletResponse.setCurrency(w.getMoney().getCurrency());
            walletResponses.add(walletResponse);
        }
        when(walletServiceInterface.getAllWallets()).thenReturn(walletResponses);
        MvcResult test1 = mockMvc.perform(get("/wallet/fetchWallets"))
                .andExpect(status().isOk())
                .andReturn();

        String response = test1.getResponse().getContentAsString();
        WalletResponse[] walletResponse = objectMapper.readValue(response, WalletResponse[].class);

        for (int i = 0; i< walletResponse.length; i++) {
            assert(walletResponse[i].getAmount().equals(walletResponses.get(i).getAmount()));
            assert(walletResponse[i].getCurrency().equals(walletResponses.get(i).getCurrency()));
        }
    }

}