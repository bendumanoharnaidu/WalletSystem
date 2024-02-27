package org.swiggy.walletsystem.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.swiggy.walletsystem.models.entites.Money;

@Data
@AllArgsConstructor
public class MoneyTransferRequest {
    private Long fromWalletId;
    private Long toWalletId;
    private String toUser;
    private Money money;
}
