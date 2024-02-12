package org.swiggy.walletsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.swiggy.walletsystem.dto.WalletDto;
import org.swiggy.walletsystem.models.entites.Wallet;
import org.swiggy.walletsystem.service.WalletInterface;
import org.swiggy.walletsystem.service.WalletService;

@RestController
@RequestMapping("/wallet")
public class WalletController {
    @Autowired
    private WalletInterface walletInterface;
    @PostMapping("/createWallet")
    public WalletDto createWallet(@RequestBody WalletDto walletDto) {
        return walletInterface.createWallet(walletDto);
    }
    @PutMapping("/{id}/addAmount")
    public WalletDto addAmount(@PathVariable long id, @RequestBody WalletDto walletDto) {
        return walletInterface.addAmount(id, walletDto.getAmount());
    }
    @PutMapping("/{id}/deductAmount")
    public WalletDto deductAmount(@PathVariable long id, @RequestBody WalletDto walletDto) {
        return walletInterface.deductAmount(id, walletDto.getAmount());
    }
    @GetMapping("/{id}/getAmount")
    public long getAmount(@PathVariable long id) {
        return walletInterface.getAmount(id);
    }
    @DeleteMapping("/{id}/deleteWallet")
    public void deleteWallet(@PathVariable long id) {
        walletInterface.deleteWallet(id);
    }
    @GetMapping("/{id}/getWallet")
    public WalletDto getWallet(@PathVariable long id) {
        return walletInterface.getWallet(id);
    }

}
