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
import java.util.List;
import java.util.Optional;

@Service
public class WalletService implements WalletServiceInterface {

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public WalletDto createWallet() {
        Wallet wallet = new Wallet();
        return toDto(walletRepository.save(wallet));
    }
    @Override
    public WalletDto addAmount(long id, BigDecimal amount, Currency currency) {
        Optional<Wallet> optionalWallet = walletRepository.findById(id);
        if(optionalWallet.isPresent()){
            Wallet wallet = optionalWallet.get();
            wallet.deposit(new Money(amount, currency));
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
            wallet1.withdraw(new Money(amount, currency));
            wallet1 =  walletRepository.save(wallet1);
            return toDto(wallet1);
        }
        return null;
    }
    public static WalletDto toDto(Wallet wallet) {
        WalletDto walletDto = new WalletDto();
        walletDto.setAmount(wallet.getMoney().getAmount());
        walletDto.setCurrency(wallet.getMoney().getCurrency());
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

    @Override
    public List<WalletDto> getAllWallets() {
        List<Wallet> walletList = walletRepository.findAll();
        if (walletList.isEmpty()) {
            throw new RuntimeException("No wallets found");
        }
        return walletList.stream().map(WalletService::toDto).toList();
    }


}
