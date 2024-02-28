package org.swiggy.walletsystem.models.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.swiggy.walletsystem.models.enums.Currency;
import org.swiggy.walletsystem.models.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.ConstraintMode.CONSTRAINT;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelfTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transactionId;

    @ManyToOne(cascade = CascadeType.ALL)
    private Wallet wallet;

    @Embedded
    private Money money;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private LocalDateTime date;
}
