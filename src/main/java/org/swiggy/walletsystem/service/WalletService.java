package org.swiggy.walletsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.swiggy.walletsystem.dto.request.MoneyTransferRequest;
import org.swiggy.walletsystem.dto.request.WalletRequest;
import org.swiggy.walletsystem.dto.response.MoneyTransferResponse;
import org.swiggy.walletsystem.dto.response.WalletResponse;
import org.swiggy.walletsystem.execptions.InsufficientMoneyException;
import org.swiggy.walletsystem.execptions.UserNotFoundException;
import org.swiggy.walletsystem.models.entites.*;
import org.swiggy.walletsystem.models.enums.Currency;
import org.swiggy.walletsystem.models.enums.TransactionType;
import org.swiggy.walletsystem.models.repository.SelfTransactionRepository;
import org.swiggy.walletsystem.models.repository.TransactionRepository;
import org.swiggy.walletsystem.models.repository.UserRepository;
import org.swiggy.walletsystem.models.repository.WalletRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WalletService implements WalletServiceInterface {

    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SelfTransactionRepository selfTransactionRepository;

    @Override
    public WalletResponse addAmountToUser(String userName, Long walletId, WalletRequest walletRequest) throws UserNotFoundException {
        UserModel user = userRepository.findByUsername(userName).orElseThrow(() -> new UserNotFoundException("User not found"));
        Wallet wallet = walletRepository.findByUserIdAndWalletId(walletId, user.getId()).orElseThrow(() -> new RuntimeException("Wallet not found"));
        Money money = new Money(walletRequest.getAmount(), walletRequest.getCurrency());
        wallet.deposit(money);
        SelfTransaction selfTransaction = SelfTransaction.builder().transactionType(TransactionType.DEPOSIT).money(money).date(LocalDateTime.now()).wallet(wallet).build();
        selfTransactionRepository.save(selfTransaction);
        Wallet returnedWallet = walletRepository.save(wallet);
        return toDto(returnedWallet);
    }

    @Override
    public WalletResponse deductAmountFromUser(String userName, Long walletId, WalletRequest walletRequest) throws InsufficientMoneyException, UserNotFoundException {
        UserModel user = userRepository.findByUsername(userName).orElseThrow(() -> new UserNotFoundException("User not found"));
        Wallet wallet = walletRepository.findByUserIdAndWalletId(walletId, user.getId()).orElseThrow(() -> new RuntimeException("Wallet not found"));
        Money money = new Money(walletRequest.getAmount(), walletRequest.getCurrency());
        wallet.withdraw(money);
        SelfTransaction selfTransaction = SelfTransaction.builder().transactionType(TransactionType.WITHDRAW).money(money).date(LocalDateTime.now()).wallet(wallet).build();
        selfTransactionRepository.save(selfTransaction);
        wallet = walletRepository.save(wallet);
        return toDto(wallet);
    }

    @Override
    public List<WalletResponse> getAllWallets(String username) {
        Optional<UserModel> optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isPresent()) {
            UserModel user = optionalUser.get();
            List<Wallet> wallets = user.getWallets();
            return wallets.stream().map(WalletService::toDto).toList();
        }
        else {
            throw new RuntimeException("User not found");
        }
    }

    public static WalletResponse toDto(Wallet wallet) {
        WalletResponse walletResponse = new WalletResponse();
        walletResponse.setWalletId(wallet.getId());
        walletResponse.setAmount(wallet.getMoney().getAmount());
        walletResponse.setCurrency(wallet.getMoney().getCurrency());
        return walletResponse;
    }
}
