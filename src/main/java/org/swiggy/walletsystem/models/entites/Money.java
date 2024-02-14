package org.swiggy.walletsystem.models.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.swiggy.walletsystem.models.enums.Currency;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Money {
    @JsonIgnore
    private BigDecimal amount ;
    @Column(name = "currency", nullable = false, columnDefinition = "varchar(255) default 'INR'")
    @Enumerated(EnumType.STRING)
    private Currency currency = Currency.INR;
    public void add(Money depositAmount) {

        if(depositAmount.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }

        this.amount = this.amount.add(Currency
                .convert(depositAmount.getCurrency(),
                depositAmount.getAmount()));
        this.currency = depositAmount.getCurrency();
    }
    public void subtract(Money withdrawAmount) {
        BigDecimal amountInBase = Currency.convert(withdrawAmount.getCurrency(), withdrawAmount.getAmount());
        System.out.println(amountInBase);
        if(withdrawAmount.getAmount().compareTo(amountInBase) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }

        this.amount = this.amount.subtract(amountInBase);
        System.out.println(this.amount);
    }

}
