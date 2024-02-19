package org.swiggy.walletsystem.dto.projections;

import org.swiggy.walletsystem.models.entites.Money;

import java.time.LocalDateTime;

public interface TransactionDetailProjection {
    Long getTransactionId();
    Money getMoney();
    String getSender();
    String getReceiver();
    LocalDateTime getTransactionDate();
}
