package org.swiggy.walletsystem.service;

import org.swiggy.walletsystem.dto.WalletDto;
import org.swiggy.walletsystem.dto.request.WalletRequest;
import org.swiggy.walletsystem.models.entites.Wallet;
import org.swiggy.walletsystem.models.enums.Currency;

import java.math.BigDecimal;

public interface WalletServiceInterface {
    WalletDto addAmount(long id, BigDecimal amount, Currency currency);
    WalletDto deductAmount(long id, BigDecimal amount, Currency currency);
    Wallet createWallet();
    BigDecimal getAmount(long id);
//    WalletDto getWallet(long id);
//    void deleteWallet(long id);

}
