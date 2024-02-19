package org.swiggy.walletsystem.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.swiggy.walletsystem.models.entites.Transaction;
import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("select t from Transaction t where t.sender.id = :userId or t.receiver.id = :userId")
    List<Transaction> findBySenderOrReceiver(Long userId);

    @Query ("select t from Transaction t where (t.sender.id = :userId or t.receiver.id = :userId) and t.date between :start and :end")
    List<Transaction> findTransactionBetweenTimestamps(Long userId, LocalDateTime start, LocalDateTime end);
    // Date Format: 2024-02-18T12:45:30
}
