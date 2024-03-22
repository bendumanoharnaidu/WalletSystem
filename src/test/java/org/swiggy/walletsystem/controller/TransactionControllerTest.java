package org.swiggy.walletsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.swiggy.walletsystem.dto.request.MoneyTransferRequest;
import org.swiggy.walletsystem.dto.response.MoneyTransferResponse;
import org.swiggy.walletsystem.dto.response.TransactionResponse;
import org.swiggy.walletsystem.execptions.InsufficientMoneyException;
import org.swiggy.walletsystem.models.entites.Money;
import org.swiggy.walletsystem.models.entites.UserModel;
import org.swiggy.walletsystem.models.entites.Wallet;
import org.swiggy.walletsystem.models.enums.Currency;
import org.swiggy.walletsystem.models.repository.UserRepository;
import org.swiggy.walletsystem.service.TransactionService;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TransactionService transactionService;
    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        reset();
    }
    @Test
    @WithMockUser(username = "firstUser", password = "password")
    void transferAmountToUser() throws Exception {
        Money firstUsersMoney = new Money(BigDecimal.valueOf(100), Currency.INR);
        Wallet firstUserWallet = new Wallet(1L, firstUsersMoney, new UserModel("firstUser", "password", new Wallet(), "INDIA"), true);
        UserModel firstUser = new UserModel("firstUser", "password", firstUserWallet, "INDIA");
        when(userRepository.findByUsername("firstUser")).thenReturn(java.util.Optional.of(firstUser));
//
        Money secondUsersMoney = new Money(BigDecimal.valueOf(100), Currency.INR);
        Wallet secondUserWallet = new Wallet(2L, secondUsersMoney, new UserModel("secondUser", "password", new Wallet(), "INDIA"), true);
        UserModel secondUser = new UserModel("secondUser", "password", secondUserWallet, "INDIA");
        when(userRepository.findByUsername("secondUser")).thenReturn(java.util.Optional.of(secondUser));

        MoneyTransferRequest moneyTransferRequest1 = new MoneyTransferRequest(1L, 2L, "secondUser", new Money(BigDecimal.valueOf(50), Currency.INR));
        MoneyTransferRequest moneyTransferRequest2 = new MoneyTransferRequest(2L, 1L, "firstUser", new Money(BigDecimal.valueOf(50), Currency.INR));

        when(transactionService.transferAmountToUser(firstUser, moneyTransferRequest1)).thenReturn(new MoneyTransferResponse("Amount transferred successfully"));
        when(transactionService.transferAmountToUser(secondUser, moneyTransferRequest2)).thenReturn(new MoneyTransferResponse("Amount transferred successfully"));

        mockMvc.perform(MockMvcRequestBuilders.put("/transactions",moneyTransferRequest1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(moneyTransferRequest1)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new MoneyTransferResponse("Amount transferred successfully"))));
        verify(transactionService, times(1)).transferAmountToUser(firstUser, moneyTransferRequest1);

    }
    @Test
    @WithMockUser(username = "firstUser", password = "password")
    void transferAmountToUser_InsufficientMoneyException() throws Exception {
        Money firstUsersMoney = new Money(BigDecimal.valueOf(100), Currency.INR);
        Wallet firstUserWallet = new Wallet(1L, firstUsersMoney, new UserModel("firstUser", "password", new Wallet(), "INDIA"), true);
        UserModel firstUser = new UserModel("firstUser", "password", firstUserWallet, "INDIA");
        when(userRepository.findByUsername("firstUser")).thenReturn(java.util.Optional.of(firstUser));

        Money secondUsersMoney = new Money(BigDecimal.valueOf(100), Currency.INR);
        Wallet secondUserWallet = new Wallet(2L, secondUsersMoney, new UserModel("secondUser", "password", new Wallet(), "INDIA"), true);
        UserModel secondUser = new UserModel("secondUser", "password", secondUserWallet, "INDIA");
        when(userRepository.findByUsername("secondUser")).thenReturn(java.util.Optional.of(secondUser));

        MoneyTransferRequest moneyTransferRequest1 = new MoneyTransferRequest(1L, 2L, "secondUser", new Money(BigDecimal.valueOf(150), Currency.INR));
        when(transactionService.transferAmountToUser(firstUser, moneyTransferRequest1)).thenThrow(new InsufficientMoneyException("Insufficient money in wallet"));

        mockMvc.perform(MockMvcRequestBuilders.put("/transactions",moneyTransferRequest1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(moneyTransferRequest1)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Insufficient money in wallet"));
    }

    @Test
    @WithMockUser(username = "firstUser", password = "password")
    void allTransactions() throws Exception {
        Money firstUsersMoney = new Money(BigDecimal.valueOf(100), Currency.INR);
        Wallet firstUserWallet = new Wallet(1L, firstUsersMoney, new UserModel("firstUser", "password", new Wallet(), "INDIA"), true);
        UserModel firstUser = new UserModel("firstUser", "password", firstUserWallet, "INDIA");
        when(userRepository.findByUsername("firstUser")).thenReturn(java.util.Optional.of(firstUser));

        Money secondUsersMoney = new Money(BigDecimal.valueOf(100), Currency.INR);
        Wallet secondUserWallet = new Wallet(2L, secondUsersMoney, new UserModel("secondUser", "password", new Wallet(), "INDIA"), true);
        UserModel secondUser = new UserModel("secondUser", "password", secondUserWallet, "INDIA");
        when(userRepository.findByUsername("secondUser")).thenReturn(java.util.Optional.of(secondUser));

        new MoneyTransferRequest(1L, 2L, "secondUser", new Money(BigDecimal.valueOf(20), Currency.INR));
        new MoneyTransferRequest(1L, 2L, "secondUser", new Money(BigDecimal.valueOf(20), Currency.INR));

        when(transactionService.fetchallTransactions("firstUser")).thenReturn(new TransactionResponse());

        mockMvc.perform(get("/transactions")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new TransactionResponse())));
    }

    @Test
    void transactionsBetween() throws Exception {
        UserModel user = new UserModel("testUser","password", new Wallet(), "INDIA");
        user.setId(1L);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        List<TransactionResponse> response = Arrays.asList(
                mock(TransactionResponse.class),
                mock(TransactionResponse.class)
        );

        when(transactionService.fetchallTransactions(user.getUsername())).thenReturn((TransactionResponse) response);

        String expectedJsonResponse = objectMapper.writeValueAsString(response);

        mockMvc.perform(get("/transactions/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJsonResponse));

        verify(transactionService, times(1)).fetchallTransactions(user.getUsername());
    }
}