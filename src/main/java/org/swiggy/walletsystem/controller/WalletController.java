package org.swiggy.walletsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.swiggy.walletsystem.dto.request.MoneyTransferRequest;
import org.swiggy.walletsystem.dto.response.MoneyTransferResponse;
import org.swiggy.walletsystem.dto.response.WalletResponse;
import org.swiggy.walletsystem.dto.request.WalletRequest;
import org.swiggy.walletsystem.execptions.InsufficientMoneyException;
import org.swiggy.walletsystem.service.UserServiceInterface;
import org.swiggy.walletsystem.service.WalletServiceInterface;

import java.util.List;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletServiceInterface walletServiceInterface;

    @Autowired
    private UserServiceInterface userServiceInterface;

    @PutMapping("/user/deposit")
    public ResponseEntity<WalletResponse> depositAmountToUser(@RequestBody WalletRequest walletRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return new ResponseEntity<>(walletServiceInterface.addAmountToUser(username, walletRequest), HttpStatus.OK);
    }
    @PutMapping("/user/withdraw")
    public ResponseEntity<WalletResponse> withdrawAmountFromUser(@RequestBody WalletRequest walletRequest) throws InsufficientMoneyException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return new ResponseEntity<>(walletServiceInterface.deductAmountFromUser(username, walletRequest), HttpStatus.OK);
    }

    @PutMapping("user/deposit/{otherUser}")
    public ResponseEntity<MoneyTransferResponse> transferAmountToUser(@PathVariable String otherUser, @RequestBody MoneyTransferRequest moneyTransferRequest) throws InsufficientMoneyException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return new ResponseEntity<>(walletServiceInterface.transferAmountToUser(username, otherUser, moneyTransferRequest), HttpStatus.OK);
    }

    @GetMapping("/fetchWallets")
    public List<WalletResponse> fetchWallets() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return walletServiceInterface.getAllWallets();
    }

}
