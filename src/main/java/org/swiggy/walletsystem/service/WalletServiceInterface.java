package org.swiggy.walletsystem.service;

import org.swiggy.walletsystem.dto.request.MoneyTransferRequest;
import org.swiggy.walletsystem.dto.request.WalletRequest;
import org.swiggy.walletsystem.dto.response.MoneyTransferResponse;
import org.swiggy.walletsystem.dto.response.WalletResponse;
import org.swiggy.walletsystem.execptions.InsufficientMoneyException;
import org.swiggy.walletsystem.models.enums.Currency;

import java.math.BigDecimal;
import java.util.List;

public interface WalletServiceInterface {

    WalletResponse addAmountToUser(String userName, WalletRequest walletRequest);

    WalletResponse deductAmountFromUser(String userName, WalletRequest walletRequest) throws InsufficientMoneyException;

    BigDecimal getAmount(long id);

    List<WalletResponse> getAllWallets();

    MoneyTransferResponse transferAmountToUser(String username, String otherUser, MoneyTransferRequest moneyTransferRequest) throws InsufficientMoneyException;
}
