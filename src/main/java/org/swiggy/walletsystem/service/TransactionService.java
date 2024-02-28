package org.swiggy.walletsystem.service;

import currencyconversion.ConvertResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.swiggy.walletsystem.dto.projections.TransactionDetailProjection;
import org.swiggy.walletsystem.dto.request.MoneyTransferRequest;
import org.swiggy.walletsystem.dto.response.MoneyTransferResponse;
import org.swiggy.walletsystem.dto.response.TransactionResponse;
import org.swiggy.walletsystem.execptions.InsufficientMoneyException;
import org.swiggy.walletsystem.execptions.UserNotFoundException;
import org.swiggy.walletsystem.grpcClient.CurrencyConversionGrpcClient;
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
public class TransactionService implements TransactionServiceInterface{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private WalletRepository walletRepository;


    @Override
    public MoneyTransferResponse transferAmountToUser(UserModel username, MoneyTransferRequest moneyTransferRequest) throws UserNotFoundException, InsufficientMoneyException {

        UserModel currentUser = getUser(username.getUsername());
        UserModel otherUser = getUser(moneyTransferRequest.getToUser());
        Transaction transaction = Transaction.builder().sender(currentUser).receiver(otherUser).money(moneyTransferRequest.getMoney()).date(LocalDateTime.now()).build();

        Wallet currentUserWallet = walletRepository.findByUserIdAndWalletId(moneyTransferRequest.getFromWalletId(), currentUser.getId()).orElseThrow(() -> new RuntimeException("Wallet not found"));
        Wallet otherUserWallet = walletRepository.findById(moneyTransferRequest.getToWalletId()).orElseThrow(() -> new RuntimeException("Wallet not found"));

        if (currentUserWallet.getMoney().getCurrency() != otherUserWallet.getMoney().getCurrency() ) {
            convertAndChargeFee(moneyTransferRequest, currentUserWallet, otherUserWallet, transaction);
        }
        else {
            currentUserWallet.withdraw(moneyTransferRequest.getMoney());
            otherUserWallet.deposit(moneyTransferRequest.getMoney());
        }

        walletRepository.save(currentUserWallet);
        walletRepository.save(otherUserWallet);
        transactionRepository.save(transaction);
        return new MoneyTransferResponse("Amount transferred successfully");
    }

    private static void convertAndChargeFee(MoneyTransferRequest moneyTransferRequest, Wallet currentUserWallet, Wallet otherUserWallet, Transaction transaction) throws InsufficientMoneyException {
        ConvertResponse response = CurrencyConversionGrpcClient.convertCurrency(currentUserWallet.getMoney().getCurrency().toString(), otherUserWallet.getMoney().getCurrency().toString(), moneyTransferRequest.getMoney().getAmount().doubleValue());

        Money serviceCharge = new Money(BigDecimal.valueOf(response.getServiceFee()), currentUserWallet.getMoney().getCurrency());
        Money amountToBeDeducted = new Money(BigDecimal.valueOf(response.getConvertedAmount()), Currency.valueOf(response.getCurrency()));

        currentUserWallet.withdraw(serviceCharge);
        transaction.setServiceCharge(serviceCharge);

        currentUserWallet.withdraw(amountToBeDeducted);
        otherUserWallet.deposit(amountToBeDeducted);
    }

    private UserModel getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public TransactionResponse fetchallTransactions(String username) {
        Optional<UserModel> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            List<TransactionDetailProjection> transactions = transactionRepository.findTransactionDetailsByUserId(optionalUser.get().getId());
            return TransactionResponse.builder().transactions(transactions).build();
        }
        return null;
    }

    @Override
        public TransactionResponse fetchTransactionsBetween(String username, String start, String end) {
        LocalDateTime startDateTime = LocalDateTime.parse(start);
        LocalDateTime endDateTime = LocalDateTime.parse(end);
        Optional<UserModel> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            List<TransactionDetailProjection> transactions = transactionRepository.findTransactionBetweenTimestamps(optionalUser.get().getId(), startDateTime, endDateTime);
            return TransactionResponse.builder().transactions(transactions).build();
        }
        return null;
    }
}
