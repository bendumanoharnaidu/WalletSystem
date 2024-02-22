package org.swiggy.walletsystem.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.swiggy.walletsystem.service.TransactionService;
import org.swiggy.walletsystem.service.TransactionServiceInterface;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.reset;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TransactionServiceInterface transactionServiceInterface;
    @MockBean
    private TransactionService transactionService;
    @BeforeEach
    void setUp() {
        reset();
    }
    @Test
    void transferAmountToUser() {

    }

    @Test
    void allTransactions() {
    }

    @Test
    void transactionsBetween() {
    }
}