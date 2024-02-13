package org.swiggy.walletsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.swiggy.walletsystem.dto.WalletDto;
import org.swiggy.walletsystem.dto.request.WalletRequest;
import org.swiggy.walletsystem.models.entites.Money;
import org.swiggy.walletsystem.models.entites.Wallet;
import org.swiggy.walletsystem.models.enums.Currency;
import org.swiggy.walletsystem.models.repository.WalletRepository;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletService implements WalletServiceInterface {
    @Autowired
    private WalletRepository walletRepository;
    @Override
    public WalletDto addAmount(long id, BigDecimal amount, Currency currency) {
        Optional<Wallet> optionalWallet = walletRepository.findById(id);
        if(optionalWallet.isPresent()){
            Wallet wallet = optionalWallet.get();
            wallet.deposit(amount, currency);
            wallet = walletRepository.save(wallet);
            return toDto(wallet);
        }
        return null;
    }
    @Override
    public WalletDto deductAmount(long id, BigDecimal amount, Currency currency) {
        Optional<Wallet> wallet = walletRepository.findById(id);
        if(wallet.isPresent()){
            Wallet wallet1 = wallet.get();
            if (wallet1.getMoney().getAmount().compareTo(amount) < 0) {
                throw new RuntimeException("Insufficient balance");
            }
            wallet1.withdraw(amount, currency);
            wallet1 =  walletRepository.save(wallet1);
            return toDto(wallet1);
        }
        return null;
    }
    @Override
    public Wallet createWallet() {
        Wallet wallet = new Wallet();
        Money money = new Money();
        money.setAmount(BigDecimal.ZERO);
        wallet.setMoney(money);
        return walletRepository.save(wallet);
    }
    public static WalletDto toDto(Wallet wallet) {
        WalletDto walletDto = new WalletDto();
        walletDto.setAmount(wallet.getMoney().getAmount());
        return walletDto;
    }
    @Override
    public BigDecimal getAmount(long id) {
        Optional<Wallet> wallet = walletRepository.findById(id);
        if(wallet.isPresent()){

            return wallet.get().getMoney().getAmount();
        }
        return BigDecimal.ZERO;
    }
//    @Override
//    public WalletDto getWallet(long id) {
//        Optional<Wallet> wallet = walletRepository.findById(id);
//        if(wallet.isPresent()){
//            return toDto(wallet.get());
//        }
//        return null;
//    }
//    @Override
//    public void deleteWallet(long id) {
//        // TODO Auto-generated method stub
//        Optional<Wallet> wallet = walletRepository.findById(id);
//        if(wallet.isPresent()){
//            walletRepository.delete(wallet.get());
//        }
//        throw new RuntimeException("Wallet not found");
//    }

}
