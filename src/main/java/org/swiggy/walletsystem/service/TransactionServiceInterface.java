package org.swiggy.walletsystem.service;

import org.swiggy.walletsystem.dto.response.TransactionResponse;

public interface TransactionServiceInterface {

    TransactionResponse fetchallTransactions(String username);
    TransactionResponse fetchTransactionsBetween(String username, String start, String end);
}
