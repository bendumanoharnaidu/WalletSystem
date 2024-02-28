package org.swiggy.walletsystem.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.swiggy.walletsystem.dto.request.MoneyTransferRequest;
import org.swiggy.walletsystem.dto.request.WalletRequest;
import org.swiggy.walletsystem.dto.response.MoneyTransferResponse;
import org.swiggy.walletsystem.dto.response.WalletResponse;
import org.swiggy.walletsystem.execptions.InsufficientMoneyException;
import org.swiggy.walletsystem.execptions.UserNotFoundException;
import org.swiggy.walletsystem.models.entites.Money;
import org.swiggy.walletsystem.models.entites.UserModel;
import org.swiggy.walletsystem.models.entites.Wallet;
import org.swiggy.walletsystem.models.enums.Currency;
import org.swiggy.walletsystem.models.repository.TransactionRepository;
import org.swiggy.walletsystem.models.repository.UserRepository;
import org.swiggy.walletsystem.models.repository.WalletRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
@SpringBootTest
class WalletServiceTest {
    @Mock
    private WalletRepository walletRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private TransactionServiceInterface transactionServiceInterface;
    @InjectMocks
    private WalletService walletService;



//    @Test
//    void testAddAmount() {
//        Wallet wallet = spy(new Wallet(1L, new Money(BigDecimal.valueOf(100), Currency.INR)));
//        String userName = "user";
//        UserModel userModel = spy(new UserModel(1L, userName, "password", wallet));
//
//        when(userRepository.findByUsername(userName)).thenReturn(Optional.of(userModel));
//        when(userModel.getWallet()).thenReturn(wallet);
//        when(walletRepository.save(wallet)).thenReturn(wallet);
//        when(userRepository.save(userModel)).thenReturn(userModel);
//
//        assertEquals(BigDecimal.valueOf(100), wallet.getMoney().getAmount());
//
//        WalletRequest walletRequest = new WalletRequest(Currency.INR, BigDecimal.valueOf(20));
//        WalletResponse walletResponse = walletService.addAmountToUser(userName, walletRequest);
//        assertEquals(BigDecimal.valueOf(120.0), walletResponse.getAmount());
//    }

//    @Test
//    void deductAmount() throws InsufficientMoneyException, UserNotFoundException {
//        Wallet wallet = spy(new Wallet(1L, new Money(BigDecimal.valueOf(100), Currency.INR)));
//        String userName = "user";
//        UserModel userModel = spy(new UserModel(1L, userName, "password", wallet));
//
//        when(userRepository.findByUsername(userName)).thenReturn(Optional.of(userModel));
//        when(userModel.getWallet()).thenReturn(wallet);
//        when(walletRepository.save(wallet)).thenReturn(wallet);
//        when(userRepository.save(userModel)).thenReturn(userModel);
//
//        assertEquals(BigDecimal.valueOf(100), wallet.getMoney().getAmount());
//
//        WalletRequest walletRequest = new WalletRequest(Currency.INR, BigDecimal.valueOf(20));
//        WalletResponse walletResponse = walletService.deductAmountFromUser(userName, walletRequest);
//        assertEquals(BigDecimal.valueOf(80.0), walletResponse.getAmount());
//
//    }

//    @Test
//    void testDeductAmountThrowsException() {
//        Wallet wallet = spy(new Wallet(1L, new Money(BigDecimal.valueOf(100), Currency.INR)));
//        String userName = "user";
//        UserModel userModel = spy(new UserModel(1L, userName, "password", wallet));
//
//        when(userRepository.findByUsername(userName)).thenReturn(Optional.of(userModel));
//        when(userModel.getWallet()).thenReturn(wallet);
//        when(walletRepository.save(wallet)).thenReturn(wallet);
//        when(userRepository.save(userModel)).thenReturn(userModel);
//
//        assertEquals(BigDecimal.valueOf(100), wallet.getMoney().getAmount());
//
//        WalletRequest walletRequest = new WalletRequest(Currency.INR, BigDecimal.valueOf(200));
//        try {
//            WalletResponse walletResponse = walletService.deductAmountFromUser(userName, walletRequest);
//        } catch (InsufficientMoneyException e) {
//            assertEquals("Insufficient balance", e.getMessage());
//        } catch (UserNotFoundException e) {
//            assertEquals("User not found", e.getMessage());
//        }
//    }

//    @Test
//    void testGetAllWallets() {
//        Wallet wallet = new Wallet(1L, new Money(BigDecimal.valueOf(100), Currency.INR));
//        when(walletRepository.findAll()).thenReturn(List.of(wallet));
//        List<WalletResponse> walletResponses = walletService.getAllWallets();
//        assertEquals(1, walletResponses.size());
//    }
//
//    @Test
//    void testGetAmount() {
//        Wallet wallet = new Wallet(1L, new Money(BigDecimal.valueOf(100), Currency.INR));
//        when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet));
//        BigDecimal amount = walletService.getAmount(1L);
//        assertEquals(BigDecimal.valueOf(100), amount);
//    }
//
//    @Test
//    void testTransferAmountToUser() throws InsufficientMoneyException, UserNotFoundException {
//        Wallet wallet = spy(new Wallet(1L, new Money(BigDecimal.valueOf(100), Currency.INR)));
//        String userName = "user";
//        UserModel user = spy(new UserModel(1L, userName, "password", wallet));
//
//        String toUserName = "other";
//        Wallet otherWallet = spy(new Wallet(2L, new Money(BigDecimal.valueOf(100), Currency.INR)));
//        UserModel otheruser = spy(new UserModel(2L, toUserName, "password", wallet));
//
//        when(userRepository.findByUsername(userName)).thenReturn(Optional.of(user));
//        when(userRepository.findByUsername(toUserName)).thenReturn(Optional.of(otheruser));
//        when(user.getWallet()).thenReturn(wallet);
//        when(otheruser.getWallet()).thenReturn(otherWallet);
//        when(walletRepository.save(wallet)).thenReturn(wallet);
//        when(walletRepository.save(otherWallet)).thenReturn(otherWallet);
//        when(userRepository.save(user)).thenReturn(user);
//        when(userRepository.save(otheruser)).thenReturn(otheruser);
//
//        assertEquals(BigDecimal.valueOf(100), wallet.getMoney().getAmount());
//
//        MoneyTransferRequest moneyTransferRequest = new MoneyTransferRequest(toUserName, "INR", 20);
//
//        MoneyTransferResponse transferAmountToUser = walletService.transferAmountToUser(toUserName, moneyTransferRequest);
//
//        assertEquals("Amount transferred successfully", transferAmountToUser.getResponse());
//
//    }
//    @Test
//void testTransferAmountToUserThrowsException() {
//        Wallet wallet = spy(new Wallet(1L, new Money(BigDecimal.valueOf(100), Currency.INR)));
//        String userName = "user";
//        UserModel userModel = spy(new UserModel(1L, userName, "password", wallet));
//
//        when(userRepository.findByUsername(userName)).thenReturn(Optional.of(userModel));
//        when(userModel.getWallet()).thenReturn(wallet);
//        when(walletRepository.save(wallet)).thenReturn(wallet);
//        when(userRepository.save(userModel)).thenReturn(userModel);
//
//        assertEquals(BigDecimal.valueOf(100), wallet.getMoney().getAmount());
//
//        WalletRequest walletRequest = new WalletRequest(Currency.INR, BigDecimal.valueOf(200));
//        try {
//            WalletResponse walletResponse = walletService.deductAmountFromUser(userName, walletRequest);
//        } catch (InsufficientMoneyException e) {
//            assertEquals("Insufficient balance", e.getMessage());
//        } catch (UserNotFoundException e) {
//            assertEquals("User not found", e.getMessage());
//        }
//    }

}