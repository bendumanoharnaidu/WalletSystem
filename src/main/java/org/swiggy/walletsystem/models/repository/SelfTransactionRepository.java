package org.swiggy.walletsystem.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.swiggy.walletsystem.models.entites.SelfTransaction;

@Repository
public interface SelfTransactionRepository extends JpaRepository<SelfTransaction, Long> {

}
