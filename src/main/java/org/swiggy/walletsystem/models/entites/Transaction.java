package org.swiggy.walletsystem.models.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transactionId;

    @Embedded
    private Money money;

    @ManyToOne(cascade = CascadeType.ALL)
    private UserModel sender;

    @ManyToOne(cascade = CascadeType.ALL)
    private UserModel receiver;

    @Column(name = "date")
    private LocalDateTime date;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "serviceChargeAmount")),
            @AttributeOverride(name = "currency", column = @Column(name = "serviceChargeCurrency"))
    })
    private Money serviceCharge;

    @Builder.Default
    @Column(name = "isActive")
    private boolean isActive=true;

}
