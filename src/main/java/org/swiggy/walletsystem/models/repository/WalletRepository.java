package org.swiggy.walletsystem.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.swiggy.walletsystem.models.entites.Wallet;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    @Query("SELECT w FROM Wallet w WHERE w.users.id = :userId AND w.id = :walletId")
    Optional<Wallet> findByUserIdAndWalletId(long walletId, long userId);
}
