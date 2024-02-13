package org.swiggy.walletsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.swiggy.walletsystem.dto.WalletDto;
import org.swiggy.walletsystem.dto.request.WalletRequest;
import org.swiggy.walletsystem.models.entites.Wallet;
import org.swiggy.walletsystem.service.WalletServiceInterface;

@RestController
@RequestMapping("/wallet")
public class WalletController {
    @Autowired
    private WalletServiceInterface walletServiceInterface;
    @PostMapping("/createWallet")
    public ResponseEntity<Wallet> createWallet() {
        return new ResponseEntity<>(walletServiceInterface.createWallet(), HttpStatus.CREATED);
    }
    @PutMapping("/{id}/addAmount")
    public ResponseEntity<WalletDto> addAmount(@PathVariable long id, @RequestBody WalletRequest walletRequest) {
        return new ResponseEntity<>(walletServiceInterface.addAmount(id, walletRequest.getAmount(), walletRequest.getCurrency()), HttpStatus.OK);
    }
    @PutMapping("/{id}/deductAmount")
    public ResponseEntity<WalletDto> deductAmount(@PathVariable long id, @RequestBody WalletRequest walletRequest) {
        return new ResponseEntity<>(walletServiceInterface.deductAmount(id, walletRequest.getAmount(), walletRequest.getCurrency()), HttpStatus.OK);
    }


//    @GetMapping("/{id}/getAmount")
//    public long getAmount(@PathVariable long id) {
//        return walletServiceInterface.getAmount(id);
//    }
//    @DeleteMapping("/{id}/deleteWallet")
//    public void deleteWallet(@PathVariable long id) {
//        walletServiceInterface.deleteWallet(id);
//    }
//    @GetMapping("/{id}/getWallet")
//    public WalletDto getWallet(@PathVariable long id) {
//        return walletServiceInterface.getWallet(id);
//    }

}
