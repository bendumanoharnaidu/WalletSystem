package org.swiggy.walletsystem.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.swiggy.walletsystem.dto.request.WalletRequest;
import org.swiggy.walletsystem.dto.response.WalletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.swiggy.walletsystem.models.entites.Money;
import org.swiggy.walletsystem.models.entites.UserModel;
import org.swiggy.walletsystem.models.entites.Wallet;
import org.swiggy.walletsystem.models.enums.Currency;
import org.swiggy.walletsystem.models.repository.UserRepository;
import org.swiggy.walletsystem.service.WalletService;
import org.swiggy.walletsystem.service.WalletServiceInterface;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    @Mock
    private WalletService walletService;
    @InjectMocks
    private WalletController walletController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(walletController).build();
    }



    @Test
    @WithMockUser(username = "user")
    void test_addAmount() throws Exception {

        WalletRequest walletRequest = new WalletRequest();
        walletRequest.setAmount(BigDecimal.valueOf(100));
        walletRequest.setCurrency(Currency.INR);
        WalletResponse walletResponse = new WalletResponse();
        walletResponse.setAmount(BigDecimal.valueOf(100));
        walletResponse.setCurrency(Currency.INR);

        when(walletServiceInterface.addAmountToUser(anyString(), any())).thenReturn(walletResponse);

        this.mockMvc.perform(put("/wallet/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(walletRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(100));
        verify(walletServiceInterface, times(1)).addAmountToUser(anyString(), any());
    }


    @Test
    @WithMockUser(username = "user", roles = "USER")
    void test_deductAmount() throws Exception {
        WalletRequest walletRequest = new WalletRequest();
        walletRequest.setAmount(BigDecimal.valueOf(100));
        walletRequest.setCurrency(Currency.INR);
        WalletResponse walletResponse = new WalletResponse();
        walletResponse.setAmount(BigDecimal.valueOf(100));
        walletResponse.setCurrency(Currency.INR);

        when(walletServiceInterface.deductAmountFromUser(anyString(), any())).thenReturn(walletResponse);

        this.mockMvc.perform(put("/wallet/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(walletRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(100));
        verify(walletServiceInterface, times(1)).deductAmountFromUser(anyString(), any());
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
        MvcResult test1 = mockMvc.perform(get("/wallet/"))
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