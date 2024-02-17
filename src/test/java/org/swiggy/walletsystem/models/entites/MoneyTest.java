package org.swiggy.walletsystem.models.entites;

import org.junit.jupiter.api.Test;
import org.swiggy.walletsystem.execptions.InsufficientMoneyException;
import org.swiggy.walletsystem.models.enums.Currency;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {
    @Test
    void getAmount() {
        Money money = new Money(new BigDecimal("100"), Currency.INR);
        Money anotherMoney = new Money(new BigDecimal("100"), Currency.INR);
        money.deposit(anotherMoney);

        assertEquals(new Money(new BigDecimal("200.0"), Currency.INR), money);
    }
    @Test
    void addTwoDifferentMoney() {
        Money money = new Money(new BigDecimal("100"), Currency.INR);
        Money money1 = new Money(new BigDecimal("1"), Currency.USD);
        money.deposit(money1);

        assertEquals(new Money(new BigDecimal("180.0"), Currency.USD), money);
    }
    @Test
    void moneyWithnegativeAmount() {
        try {
            Money money = new Money(new BigDecimal("-100"), Currency.INR);
        } catch (IllegalArgumentException e) {
            assertEquals("Amount cannot be negative", e.getMessage());
        }
    }
    @Test
    public void subtractValidMoney() throws InsufficientMoneyException {
        Money money = new Money(new BigDecimal("100"), Currency.INR);
        Money money1 = new Money(new BigDecimal("100"), Currency.INR);
        money.withdraw(money1);

        assertEquals(new Money(new BigDecimal("0.0"), Currency.INR), money);
    }

    @Test
    public void subtractMoneyFromLesserMoney() {
        Money money = new Money(new BigDecimal("100"), Currency.INR);
        Money money1 = new Money(new BigDecimal("200"), Currency.USD);
        try {
            money.withdraw(money1);
        } catch (InsufficientMoneyException e) {
            assertEquals("Amount cannot be negative", e.getMessage());
        }
    }
//    @Test
//    public void subtractTwoValidDifferentMoney() {
//        Money money = new Money(new BigDecimal("100"), Currency.INR);
//        Money money1 = new Money(new BigDecimal("1"), Currency.USD);
//        money.subtract(money1);
//
//        assertEquals(new Money(new BigDecimal("20.0"), Currency.INR), money);
//    }
}