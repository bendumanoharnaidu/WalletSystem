package org.swiggy.walletsystem.models.entites;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.swiggy.walletsystem.models.enums.Currency;
import java.math.BigDecimal;
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
        verify(money, times(1)).add(depositmoney);

    }
    @Test
    void expectMoneyToBeWithdrawn() {
        Money withdramoney = new Money(new BigDecimal("100"), Currency.INR);
        wallet.withdraw(withdramoney);
        verify(money, times(1)).subtract(withdramoney);
    }


}