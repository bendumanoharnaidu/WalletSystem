package org.swiggy.walletsystem.models.entites;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.swiggy.walletsystem.execptions.InsufficientMoneyException;
import org.swiggy.walletsystem.models.enums.Currency;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Money {

    private BigDecimal amount ;

    @Enumerated
    private Currency currency;

    public void deposit(Money depositAmount) {

        if(depositAmount.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        BigDecimal amountInBase = Currency.convert(depositAmount.getCurrency(), depositAmount.getAmount());
        BigDecimal conversionValue = BigDecimal.valueOf(currency.getConversionRate());
        this.amount = this.amount.add(amountInBase.multiply(conversionValue));

    }

    public void withdraw(Money withdrawAmount) throws InsufficientMoneyException {

        BigDecimal amountInBase = Currency.convert(withdrawAmount.getCurrency(), withdrawAmount.getAmount());
        BigDecimal conversionValue = BigDecimal.valueOf(currency.getConversionRate());

        if(this.amount.compareTo(amountInBase.multiply(conversionValue)) < 0) {
            throw new InsufficientMoneyException("Insufficient balance");

        }
        this.amount = this.amount.subtract(amountInBase.multiply(conversionValue));
    }

}
