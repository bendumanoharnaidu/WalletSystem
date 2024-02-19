package org.swiggy.walletsystem.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.swiggy.walletsystem.models.entites.Wallet;
@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

}
