package org.swiggy.walletsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.swiggy.walletsystem.models.enums.Currency;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletDto {
    private BigDecimal amount;
    private Currency currency;
}
