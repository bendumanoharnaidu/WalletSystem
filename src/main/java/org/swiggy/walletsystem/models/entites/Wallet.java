package org.swiggy.walletsystem.models.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.swiggy.walletsystem.execptions.InsufficientMoneyException;
import org.swiggy.walletsystem.models.enums.Currency;

import java.math.BigDecimal;

import static jakarta.persistence.ConstraintMode.CONSTRAINT;

@Builder
@Data
@AllArgsConstructor
@Entity
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Money money;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "user_id",foreignKey = @ForeignKey(value = CONSTRAINT,
            foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES user_model(id) ON DELETE CASCADE"))
    private UserModel users;

    @Builder.Default
    @Column(name = "isActive")
    private boolean isActive = true;

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
