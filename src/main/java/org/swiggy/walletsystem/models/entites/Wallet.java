package org.swiggy.walletsystem.models.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import currencyconversion.ConvertResponse;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.swiggy.walletsystem.dto.request.MoneyTransferRequest;
import org.swiggy.walletsystem.execptions.InsufficientMoneyException;
import org.swiggy.walletsystem.grpcClient.CurrencyConversionGrpcClient;
import org.swiggy.walletsystem.models.enums.Currency;
import org.swiggy.walletsystem.models.repository.UserRepository;

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


    public void transfer(Wallet currentUserWallet ,Wallet otherUserWallet, Transaction transaction, MoneyTransferRequest moneyTransferRequest) throws InsufficientMoneyException {
        if (currentUserWallet.getMoney().getCurrency() != otherUserWallet.getMoney().getCurrency() ) {
            convertAndChargeFee(moneyTransferRequest, currentUserWallet, otherUserWallet, transaction);
        }
        else {
            currentUserWallet.withdraw(moneyTransferRequest.getMoney());
            otherUserWallet.deposit(moneyTransferRequest.getMoney());
        }
    }
    private static void convertAndChargeFee(MoneyTransferRequest moneyTransferRequest, Wallet currentUserWallet, Wallet otherUserWallet, Transaction transaction) throws InsufficientMoneyException {
        ConvertResponse response = CurrencyConversionGrpcClient.convertCurrency(currentUserWallet.getMoney().getCurrency().toString(), otherUserWallet.getMoney().getCurrency().toString(), moneyTransferRequest.getMoney().getAmount().doubleValue());

        Money serviceCharge = new Money(BigDecimal.valueOf(response.getServiceFee()), currentUserWallet.getMoney().getCurrency());
        Money amountToBeDeducted = new Money(BigDecimal.valueOf(response.getConvertedAmount()), Currency.valueOf(response.getCurrency()));

        currentUserWallet.withdraw(serviceCharge);
        transaction.setServiceCharge(serviceCharge);

        currentUserWallet.withdraw(amountToBeDeducted);
        otherUserWallet.deposit(amountToBeDeducted);
    }


}
