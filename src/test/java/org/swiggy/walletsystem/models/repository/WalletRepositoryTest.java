package org.swiggy.walletsystem.models.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.swiggy.walletsystem.models.entites.Wallet;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class WalletRepositoryTest {

    @Autowired
    private WalletRepository walletRepository;
    Wallet wallet;
    @BeforeEach
    void setUp() {
        wallet = new Wallet();
        wallet.getMoney().setAmount(BigDecimal.valueOf(100));
        walletRepository.save(wallet);
    }

    @AfterEach
    void tearDown() {
        wallet = null;
        walletRepository.deleteAll();
    }

//    @Test
//    void findByAmountGreaterThan() {
//        List<Wallet> walletList = walletRepository.findByAmountGreaterThan(50L);
//        assertThat(walletList.get(0).getAmount()).isEqualTo(wallet.getAmount());
//        assertThat(walletList.get(0).getId()).isEqualTo(wallet.getId());
//
//    }
//
//    @Test
//    void findByAmountLessThan() {
//        List<Wallet> walletList = walletRepository.findByAmountLessThan(50L);
//        assertTrue(walletList.isEmpty());
//
//        List<Wallet> walletList1 = walletRepository.findByAmountLessThan(150L);
//        assertThat(walletList1.get(0).getAmount()).isEqualTo(wallet.getAmount());
//    }
//
//    @Test
//    void findByAmountBetween() {
//        List<Wallet> walletList = walletRepository.findByAmountBetween(50, 150);
//        assertThat(walletList.get(0).getAmount()).isEqualTo(wallet.getAmount());
//    }
}