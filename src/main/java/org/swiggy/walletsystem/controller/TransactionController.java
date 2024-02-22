package org.swiggy.walletsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.swiggy.walletsystem.dto.request.MoneyTransferRequest;
import org.swiggy.walletsystem.dto.response.MoneyTransferResponse;
import org.swiggy.walletsystem.dto.response.TransactionResponse;
import org.swiggy.walletsystem.execptions.InsufficientMoneyException;
import org.swiggy.walletsystem.execptions.UserNotFoundException;
import org.swiggy.walletsystem.service.TransactionService;
import org.swiggy.walletsystem.service.WalletServiceInterface;

@RestController
@RequestMapping("/user")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private WalletServiceInterface walletServiceInterface;

    @PutMapping("/amount-transfer")
    public ResponseEntity<MoneyTransferResponse> transferAmountToUser(@RequestBody MoneyTransferRequest moneyTransferRequest) throws InsufficientMoneyException, UserNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return new ResponseEntity<>(walletServiceInterface.transferAmountToUser(username,    moneyTransferRequest), HttpStatus.OK);
    }

    @GetMapping("/transfer-history")
    public ResponseEntity<TransactionResponse> allTransactions() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return new ResponseEntity<>(transactionService.fetchallTransactions(username), HttpStatus.OK);
    }
    
    @GetMapping("/transfer-history/range")
    public ResponseEntity<TransactionResponse> transactionsBetween(@RequestParam String start, @RequestParam String end) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return new ResponseEntity<>(transactionService.fetchTransactionsBetween(username,start,end) , HttpStatus.OK);
    }

}
