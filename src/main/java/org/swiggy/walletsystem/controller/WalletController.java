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
import org.swiggy.walletsystem.execptions.UserNotFoundException;
import org.swiggy.walletsystem.service.UserServiceInterface;
import org.swiggy.walletsystem.service.WalletServiceInterface;

import java.util.List;

@RestController
@RequestMapping("/wallets")
public class WalletController {

    @Autowired
    private WalletServiceInterface walletServiceInterface;

    @Autowired
    private UserServiceInterface userServiceInterface;

    @PutMapping("/deposit")
    public ResponseEntity<WalletResponse> depositAmountToUser(@RequestBody WalletRequest walletRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return new ResponseEntity<>(walletServiceInterface.addAmountToUser(username, walletRequest), HttpStatus.OK);
    }

    @PutMapping("/withdraw")
    public ResponseEntity<WalletResponse> withdrawAmountFromUser(@RequestBody WalletRequest walletRequest) throws InsufficientMoneyException, UserNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return new ResponseEntity<>(walletServiceInterface.deductAmountFromUser(username, walletRequest), HttpStatus.OK);
    }

    @GetMapping("")
    public List<WalletResponse> fetchWallets(@RequestParam String username) {
        return walletServiceInterface.getAllWallets(username);
    }


}
