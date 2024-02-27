package org.swiggy.walletsystem.service;

import org.swiggy.walletsystem.dto.request.MoneyTransferRequest;
import org.swiggy.walletsystem.dto.response.MoneyTransferResponse;
import org.swiggy.walletsystem.dto.response.TransactionResponse;
import org.swiggy.walletsystem.execptions.InsufficientMoneyException;
import org.swiggy.walletsystem.execptions.UserNotFoundException;
import org.swiggy.walletsystem.models.entites.UserModel;

public interface TransactionServiceInterface {

    TransactionResponse fetchallTransactions(String username);
    TransactionResponse fetchTransactionsBetween(String username, String start, String end);
    MoneyTransferResponse transferAmountToUser(UserModel username, MoneyTransferRequest moneyTransferRequest) throws UserNotFoundException, InsufficientMoneyException;
}
