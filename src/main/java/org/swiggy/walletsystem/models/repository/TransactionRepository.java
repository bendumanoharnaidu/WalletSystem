package org.swiggy.walletsystem.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.swiggy.walletsystem.dto.projections.TransactionDetailProjection;
import org.swiggy.walletsystem.models.entites.Transaction;
import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("select t from Transaction t where t.sender.id = :userId or t.receiver.id = :userId")
    List<Transaction> findBySenderOrReceiver(Long userId);

    @Query ("select t.transactionId as transactionId, t.money as money, t.sender.username as sender, t.receiver.username as receiver, t.date as transactionDate  from Transaction t where (t.sender.id = :userId or t.receiver.id = :userId) and t.date between :start and :end")
    List<TransactionDetailProjection> findTransactionBetweenTimestamps(Long userId, LocalDateTime start, LocalDateTime end);
    // Date Format: 2024-02-18T12:45:30

    @Query("select t.transactionId as transactionId, t.money as money, t.sender.username as sender, t.receiver.username as receiver, t.date as transactionDate from Transaction t where t.sender.id = :userId or t.receiver.id = :userId")
    List<TransactionDetailProjection> findTransactionDetailsByUserId(Long userId);

}
