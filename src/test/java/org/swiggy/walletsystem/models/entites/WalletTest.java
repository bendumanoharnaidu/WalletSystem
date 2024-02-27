package org.swiggy.walletsystem.models.entites;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.swiggy.walletsystem.execptions.InsufficientMoneyException;
import org.swiggy.walletsystem.models.enums.Currency;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class WalletTest {
    @Mock
    private Money money;
    @InjectMocks
    private Wallet wallet;
    @BeforeEach
    void setUp() {
        openMocks(this);
    }
    @Test
    void expectMoneyToBeDeposited() {
        Money depositmoney = new Money(new BigDecimal("100"), Currency.INR);
        wallet.deposit(depositmoney);
        verify(money, times(1)).deposit(depositmoney);

    }
    @Test
    void expectMoneyToBeWithdrawn() throws InsufficientMoneyException {
        Money withdramoney = new Money(new BigDecimal("100"), Currency.INR);
        wallet.withdraw(withdramoney);
        verify(money, times(1)).withdraw(withdramoney);
    }
    @Test
    void withdrawWithInsufficientBalance() throws InsufficientMoneyException {
        Money withdramoney = new Money(new BigDecimal("100"), Currency.INR);
        doThrow(new IllegalArgumentException("Amount cannot be negative")).when(money).withdraw(withdramoney);
    try {
            wallet.withdraw(withdramoney);
        } catch (IllegalArgumentException | InsufficientMoneyException e) {
            assertEquals("Amount cannot be negative", e.getMessage());
        }
    }


}