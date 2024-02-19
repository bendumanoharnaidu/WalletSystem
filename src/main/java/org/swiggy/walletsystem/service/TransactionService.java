package org.swiggy.walletsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.swiggy.walletsystem.dto.projections.TransactionDetailProjection;
import org.swiggy.walletsystem.dto.response.TransactionResponse;
import org.swiggy.walletsystem.models.entites.Transaction;
import org.swiggy.walletsystem.models.entites.UserModel;
import org.swiggy.walletsystem.models.repository.TransactionRepository;
import org.swiggy.walletsystem.models.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class TransactionService implements TransactionServiceInterface{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;

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
