package org.swiggy.walletsystem.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.swiggy.walletsystem.models.entites.Wallet;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

//        List<Wallet> findByAmountGreaterThan(long amount);
//        List<Wallet> findByAmountLessThan(long amount);
//        List<Wallet> findByAmountBetween(long amount1, long amount2);
}
