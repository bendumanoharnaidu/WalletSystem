package org.swiggy.walletsystem.models.enums;

import java.math.BigDecimal;

public enum Currency {

    INR("INR",1.00),
    USD("USD",80.00);
    private String name;

    private double conversionRate;

    Currency(String name, double v) {
        this.conversionRate = v;
        this.name = name;
    }
    public static BigDecimal convert(Currency currency, BigDecimal amount) {
        return amount.multiply(BigDecimal.valueOf(currency.conversionRate)) ;
    }
}
