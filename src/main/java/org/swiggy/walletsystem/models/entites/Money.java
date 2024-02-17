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
    private Currency currency = Currency.INR;

    public void deposit(Money depositAmount) {

        if(depositAmount.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }

        this.amount = this.amount.add(Currency
                .convert(depositAmount.getCurrency(),
                depositAmount.getAmount()));
    }
    public void withdraw(Money withdrawAmount) throws InsufficientMoneyException {

        BigDecimal amountInBase = Currency.convert(withdrawAmount.getCurrency(), withdrawAmount.getAmount());
        System.out.println(amountInBase);

        if(withdrawAmount.getAmount().compareTo(amountInBase) < 0) {
            throw new InsufficientMoneyException("Insufficient balance");
        }

        this.amount = this.amount.subtract(amountInBase);
        System.out.println(this.amount);
    }

}
