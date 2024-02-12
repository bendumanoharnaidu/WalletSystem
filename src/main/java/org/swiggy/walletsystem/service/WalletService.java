package org.swiggy.walletsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.swiggy.walletsystem.dto.WalletDto;
import org.swiggy.walletsystem.models.entites.Wallet;
import org.swiggy.walletsystem.models.repository.WalletRepository;

import java.util.Optional;

@Service
public class WalletService implements WalletInterface{
    @Autowired
    private WalletRepository walletRepository;
    @Override
    public WalletDto addAmount(long id, long amount) {
        Optional<Wallet> wallet = walletRepository.findById(id);
        if(wallet.isPresent()){
            Wallet wallet1 = wallet.get();
            wallet1.setAmount(wallet1.getAmount() + amount);
            wallet1 = walletRepository.save(wallet1);
            return toDto(wallet1);
        }
        return null;
    }
    @Override
    public WalletDto deductAmount(long id, long amount) {
        Optional<Wallet> wallet = walletRepository.findById(id);
        if(wallet.isPresent()){
            Wallet wallet1 = wallet.get();
            wallet1.setAmount(wallet1.getAmount() - amount);
            wallet1 =  walletRepository.save(wallet1);
            return toDto(wallet1);
        }
        return null;
    }
    @Override
    public void deleteWallet(long id) {
        // TODO Auto-generated method stub
        Optional<Wallet> wallet = walletRepository.findById(id);
        if(wallet.isPresent()){
            walletRepository.delete(wallet.get());
        }
    }
    @Override
    public long getAmount(long id) {
        Optional<Wallet> wallet = walletRepository.findById(id);
        if(wallet.isPresent()){
            return wallet.get().getAmount();
        }
        return 0;
    }
    @Override
    public WalletDto createWallet(WalletDto walletDto) {
        Wallet wallet = new Wallet();
        wallet.setAmount(walletDto.getAmount());
        wallet = walletRepository.save(wallet);
        return toDto(wallet);
    }
    private WalletDto toDto(Wallet wallet) {
        WalletDto walletDto = new WalletDto();
        walletDto.setId(wallet.getId());
        walletDto.setAmount(wallet.getAmount());
        return walletDto;
    }
    @Override
    public WalletDto getWallet(long id) {
        Optional<Wallet> wallet = walletRepository.findById(id);
        if(wallet.isPresent()){
            return toDto(wallet.get());
        }
        return null;
    }

}
