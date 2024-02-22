package org.swiggy.walletsystem.models.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.swiggy.walletsystem.execptions.InsufficientMoneyException;
import org.swiggy.walletsystem.models.enums.Currency;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Entity
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Money money;
    public Wallet() {}

    public Wallet(Currency currency) {
        money = new Money(BigDecimal.ZERO, currency);
    }

    public void deposit(Money depositmoney) {
        money.deposit(depositmoney);
    }

    public void withdraw(Money withdramoney) throws InsufficientMoneyException {
        money.withdraw(withdramoney);
    }

}
