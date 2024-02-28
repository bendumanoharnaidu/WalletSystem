package org.swiggy.walletsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.swiggy.walletsystem.dto.projections.TransactionDetailProjection;
import org.swiggy.walletsystem.dto.request.MoneyTransferRequest;
import org.swiggy.walletsystem.dto.response.MoneyTransferResponse;
import org.swiggy.walletsystem.dto.response.TransactionResponse;
import org.swiggy.walletsystem.execptions.InsufficientMoneyException;
import org.swiggy.walletsystem.execptions.UserNotFoundException;
import org.swiggy.walletsystem.models.entites.Transaction;
import org.swiggy.walletsystem.models.entites.UserModel;
import org.swiggy.walletsystem.models.entites.Wallet;
import org.swiggy.walletsystem.models.repository.TransactionRepository;
import org.swiggy.walletsystem.models.repository.UserRepository;
import org.swiggy.walletsystem.models.repository.WalletRepository;
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
        UserModel currentUser = userRepository.findByUsername(username.getUsername()).orElseThrow(() -> new UserNotFoundException("User not found"));
        UserModel otherUser = userRepository.findByUsername(moneyTransferRequest.getToUser()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Transaction transaction = Transaction.builder().sender(currentUser).receiver(otherUser).money(moneyTransferRequest.getMoney()).date(LocalDateTime.now()).build();
        Wallet currentUserWallet = walletRepository.findByUserIdAndWalletId(moneyTransferRequest.getFromWalletId(), currentUser.getId()).orElseThrow(() -> new RuntimeException("Wallet not found"));
        Wallet otherUserWallet = walletRepository.findById(moneyTransferRequest.getToWalletId()).orElseThrow(() -> new RuntimeException("Wallet not found"));
        currentUserWallet.transfer(currentUserWallet,otherUserWallet,transaction, moneyTransferRequest);  //In WalletClass
        walletRepository.save(currentUserWallet);
        walletRepository.save(otherUserWallet);
        transactionRepository.save(transaction);
        return new MoneyTransferResponse("Amount transferred successfully");
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
