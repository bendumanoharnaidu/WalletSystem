package org.swiggy.walletsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.swiggy.walletsystem.dto.request.MoneyTransferRequest;
import org.swiggy.walletsystem.dto.request.WalletRequest;
import org.swiggy.walletsystem.dto.response.MoneyTransferResponse;
import org.swiggy.walletsystem.dto.response.WalletResponse;
import org.swiggy.walletsystem.execptions.InsufficientMoneyException;
import org.swiggy.walletsystem.execptions.UserNotFoundException;
import org.swiggy.walletsystem.models.entites.Money;
import org.swiggy.walletsystem.models.entites.Transaction;
import org.swiggy.walletsystem.models.entites.UserModel;
import org.swiggy.walletsystem.models.entites.Wallet;
import org.swiggy.walletsystem.models.enums.Currency;
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
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public WalletResponse addAmountToUser(String userName, WalletRequest walletRequest) {
        Optional<UserModel> optionalUser = userRepository.findByUsername(userName);
        if(optionalUser.isPresent()){
            UserModel user = optionalUser.get();
            Wallet wallet = user.getWallet();
            wallet.deposit(new Money(walletRequest.getAmount(), walletRequest.getCurrency()));

            Wallet returnedWallet = walletRepository.save(wallet);
            return toDto(returnedWallet);
        }
        else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public WalletResponse deductAmountFromUser(String userName, WalletRequest walletRequest) throws InsufficientMoneyException, UserNotFoundException {
        Optional<UserModel> optionalUser = userRepository.findByUsername(userName);
        if(optionalUser.isPresent()) {
            UserModel user = optionalUser.get();
            Wallet wallet = user.getWallet();
            if (wallet.getMoney().getAmount().compareTo(walletRequest.getAmount()) < 0) {
                throw new InsufficientMoneyException("Insufficient balance");
            }
            wallet.withdraw(new Money(walletRequest.getAmount(), walletRequest.getCurrency()));
            wallet = walletRepository.save(wallet);
            return toDto(wallet);
        }
        else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public List<WalletResponse> getAllWallets() {
        List<Wallet> walletList = walletRepository.findAll();
        if (walletList.isEmpty()) {
            throw new RuntimeException("No wallets found");
        }
        return walletList.stream().map(WalletService::toDto).toList();
    }

    @Override
    public MoneyTransferResponse transferAmountToUser(String username, MoneyTransferRequest moneyTransferRequest) throws UserNotFoundException, InsufficientMoneyException {
        Optional<UserModel> currentUser = userRepository.findByUsername(username);
        Optional<UserModel> otherUser = userRepository.findByUsername(moneyTransferRequest.getToUser());

        if(currentUser.isPresent() && otherUser.isPresent()) {
            UserModel user = currentUser.get();
            UserModel otherUserModel = otherUser.get();

            Wallet wallet = user.getWallet();
            Wallet otherWallet = otherUserModel.getWallet();

            if (wallet.getMoney().getAmount().compareTo(BigDecimal.valueOf(moneyTransferRequest.getAmount())) < 0) {
                throw new InsufficientMoneyException("Insufficient balance");
            }

            wallet.withdraw(new Money(BigDecimal.valueOf(moneyTransferRequest.getAmount()), Currency.valueOf(moneyTransferRequest.getCurrency())));
            otherWallet.deposit(new Money(BigDecimal.valueOf(moneyTransferRequest.getAmount()), Currency.valueOf(moneyTransferRequest.getCurrency())));
            walletRepository.save(wallet);
            walletRepository.save(otherWallet);
            // save transaction
            Money money = new Money(BigDecimal.valueOf(moneyTransferRequest.getAmount()), Currency.valueOf(moneyTransferRequest.getCurrency()));
            Transaction transaction = Transaction.builder()
                    .sender(user)
                    .receiver(otherUserModel)
                    .money(money)
                    .date(LocalDateTime.now())
                    .build();
            transactionRepository.save(transaction);

            return new MoneyTransferResponse("Amount transferred successfully");
        }
        else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public BigDecimal getAmount(long id) {
        Optional<Wallet> wallet = walletRepository.findById(id);
        if(wallet.isPresent()){
            return wallet.get().getMoney().getAmount();
        }
        return BigDecimal.ZERO;
    }

    public static WalletResponse toDto(Wallet wallet) {
        WalletResponse walletResponse = new WalletResponse();
        walletResponse.setWalletId(wallet.getId());
        walletResponse.setAmount(wallet.getMoney().getAmount());
        walletResponse.setCurrency(wallet.getMoney().getCurrency());
        return walletResponse;
    }

}
