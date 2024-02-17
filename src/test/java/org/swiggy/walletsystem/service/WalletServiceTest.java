package org.swiggy.walletsystem.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.swiggy.walletsystem.dto.response.WalletResponse;
import org.swiggy.walletsystem.execptions.InsufficientMoneyException;
import org.swiggy.walletsystem.models.entites.Money;
import org.swiggy.walletsystem.models.entites.Wallet;
import org.swiggy.walletsystem.models.enums.Currency;
import org.swiggy.walletsystem.models.repository.WalletRepository;

import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
@SpringBootTest
class WalletServiceTest {
    @Mock
    private WalletRepository walletRepository;
    @InjectMocks
    private WalletService walletService;

    @Test
    void createWallet() {
        Wallet wallet = new Wallet();

        when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet));
        BigDecimal balance = walletService.getAmount(1L);
        assertEquals(BigDecimal.valueOf(0), balance);
    }
    @Test
    void deductAmount() throws InsufficientMoneyException {
        Wallet wallet = new Wallet();
        Money money = new Money();
        money.setAmount(BigDecimal.valueOf(100));
        wallet.setMoney(money);
        when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet));
        BigDecimal deductAmount = BigDecimal.valueOf(20);
        when(walletRepository.save(wallet)).thenReturn(wallet);
        WalletResponse walletResponse = walletService.deductAmount(1L,deductAmount, Currency.INR);
        assertEquals(BigDecimal.valueOf(80.0), walletResponse.getAmount());
    }
    @Test
    void testAddAmount() {
        Wallet wallet = new Wallet();
        Money money = new Money();
        money.setAmount(BigDecimal.valueOf(0));
        wallet.setMoney(money);
        when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet));
        BigDecimal depositAmount = BigDecimal.valueOf(20);
            when(walletRepository.save(wallet)).thenReturn(wallet);
        WalletResponse walletResponse = walletService.addAmount(1L,depositAmount, Currency.INR);
        assertEquals(BigDecimal.valueOf(20.0), walletResponse.getAmount());
    }


}