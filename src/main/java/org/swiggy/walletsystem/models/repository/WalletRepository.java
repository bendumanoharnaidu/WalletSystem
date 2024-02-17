package org.swiggy.walletsystem.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.swiggy.walletsystem.models.entites.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

}
