package org.swiggy.walletsystem.models.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.swiggy.walletsystem.models.enums.Currency;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Money money;

    public void deposit(BigDecimal amount, Currency currency) {

        money.add(currency.convert(currency, amount));
    }
    public void withdraw(BigDecimal amount, Currency currency) {
        money.subtract(currency.convert(currency, amount));
    }

}
