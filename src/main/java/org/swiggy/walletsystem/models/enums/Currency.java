package org.swiggy.walletsystem.models.enums;

import java.math.BigDecimal;

public enum Currency {

    INR(1.00),
    USD(80.00);

    private double conversionRate;

    Currency(double v) {
        this.conversionRate = v;
    }
    public static BigDecimal convert(Currency currency, BigDecimal amount) {
        return amount.multiply(BigDecimal.valueOf(currency.conversionRate)) ;
    }
}
