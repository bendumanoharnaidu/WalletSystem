package org.swiggy.walletsystem.models.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.swiggy.walletsystem.models.enums.Currency;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Money {
    @JsonIgnore
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private Currency currency = Currency.INR;

    public void add(BigDecimal amount) {
        this.amount = this.amount.add(amount);
    }
    public void subtract(BigDecimal amount) {
        this.amount = this.amount.subtract(amount);
    }

}
