package org.swiggy.walletsystem.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.swiggy.walletsystem.dto.request.MoneyTransferRequest;
import org.swiggy.walletsystem.dto.response.MoneyTransferResponse;
import org.swiggy.walletsystem.execptions.InsufficientMoneyException;
import org.swiggy.walletsystem.execptions.UserNotFoundException;
import org.swiggy.walletsystem.models.entites.Money;
import org.swiggy.walletsystem.models.entites.UserModel;
import org.swiggy.walletsystem.models.entites.Wallet;
import org.swiggy.walletsystem.models.enums.Currency;
import org.swiggy.walletsystem.models.repository.UserRepository;
import org.swiggy.walletsystem.service.TransactionService;
import org.swiggy.walletsystem.service.TransactionServiceInterface;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TransactionServiceInterface transactionServiceInterface;
    @MockBean
    private UserRepository userRepository;
    @BeforeEach
    void setUp() {
        reset();
    }
    @Test
    void transferAmountToUser() throws UserNotFoundException, InsufficientMoneyException, JsonProcessingException {
        Money firstUsersMoney = new Money(BigDecimal.valueOf(100), Currency.INR);
        Wallet firstUserWallet = new Wallet(1L, firstUsersMoney, new UserModel("firstUser", "password", new Wallet(), "INDIA"), true);
        UserModel firstUser = new UserModel("firstUser", "password", firstUserWallet, "INDIA");
        userRepository.save(firstUser);

        Money secondUsersMoney = new Money(BigDecimal.valueOf(100), Currency.INR);
        Wallet secondUserWallet = new Wallet(2L, secondUsersMoney, new UserModel("secondUser", "password", new Wallet(), "INDIA"), true);
        UserModel secondUser = new UserModel("secondUser", "password", secondUserWallet, "INDIA");
        userRepository.save(secondUser);

        MoneyTransferRequest moneyTransferRequest1 = new MoneyTransferRequest(1L, 2L, "secondUser", new Money(BigDecimal.valueOf(50), Currency.INR));
        MoneyTransferRequest moneyTransferRequest2 = new MoneyTransferRequest(2L, 1L, "firstUser", new Money(BigDecimal.valueOf(50), Currency.INR));

        when(transactionServiceInterface.transferAmountToUser(firstUser, moneyTransferRequest1)).thenReturn(new MoneyTransferResponse("Amount transferred successfully"));
        when(transactionServiceInterface.transferAmountToUser(secondUser, moneyTransferRequest2)).thenReturn(new MoneyTransferResponse("Amount transferred successfully"));

//        mockMvc.perform(put("/transactions/")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(moneyTransferRequest1)))
//                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(new MoneyTransferResponse("Amount transferred successfully"))));
    }

    @Test
    void allTransactions() {
    }

    @Test
    void transactionsBetween() {
    }
}