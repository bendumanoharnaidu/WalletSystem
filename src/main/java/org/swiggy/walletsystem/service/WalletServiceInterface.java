package org.swiggy.walletsystem.service;

import org.swiggy.walletsystem.dto.request.MoneyTransferRequest;
import org.swiggy.walletsystem.dto.request.WalletRequest;
import org.swiggy.walletsystem.dto.response.MoneyTransferResponse;
import org.swiggy.walletsystem.dto.response.WalletResponse;
import org.swiggy.walletsystem.execptions.InsufficientMoneyException;
import org.swiggy.walletsystem.execptions.UserNotFoundException;
import org.swiggy.walletsystem.models.entites.UserModel;

import java.math.BigDecimal;
import java.util.List;

public interface WalletServiceInterface {

    WalletResponse addAmountToUser(String userName,Long walletId, WalletRequest walletRequest) throws UserNotFoundException;

    WalletResponse deductAmountFromUser(String userName,Long walletId, WalletRequest walletRequest) throws InsufficientMoneyException, UserNotFoundException;

    BigDecimal getAmount(long id);

    List<WalletResponse> getAllWallets(String username);

}
