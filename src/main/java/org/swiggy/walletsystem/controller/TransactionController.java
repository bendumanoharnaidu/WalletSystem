package org.swiggy.walletsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.swiggy.walletsystem.dto.response.TransactionResponse;
import org.swiggy.walletsystem.service.TransactionService;

@RestController
@RequestMapping("/user")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @GetMapping("/transactions/all")
    public ResponseEntity<TransactionResponse> allTransactions() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return new ResponseEntity<>(transactionService.fetchallTransactions(username), HttpStatus.OK);
    }
    @GetMapping("/transactions/between")
    public ResponseEntity<TransactionResponse> transactionsBetween(@RequestParam String start, @RequestParam String end) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return new ResponseEntity<>(transactionService.fetchTransactionsBetween(username,start,end) , HttpStatus.OK);
    }
}
