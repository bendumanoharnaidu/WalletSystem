package org.swiggy.walletsystem.service;

import org.swiggy.walletsystem.dto.WalletDto;

public interface WalletInterface {
    WalletDto addAmount(long id, long amount);
    WalletDto deductAmount(long id, long amount);
    void deleteWallet(long id);
    long getAmount(long id);
    WalletDto createWallet(WalletDto walletDto);
    WalletDto getWallet(long id);
}
