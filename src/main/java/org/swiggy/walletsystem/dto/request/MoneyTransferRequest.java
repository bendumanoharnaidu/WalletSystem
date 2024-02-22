package org.swiggy.walletsystem.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MoneyTransferRequest {
    private Long fromWalletId;
    private Long toWalletId;
    private String toUser;
    private String currency;
    private double amount;
}
