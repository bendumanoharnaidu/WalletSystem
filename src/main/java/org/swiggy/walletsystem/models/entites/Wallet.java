package org.swiggy.walletsystem.models.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    public Wallet() {
        money = new Money(BigDecimal.ZERO, Currency.INR);
    }

    public void deposit(Money depositmoney) {
        money.add(depositmoney);
    }
    public void withdraw(Money withdramoney) {
        money.subtract(withdramoney);
    }

}
