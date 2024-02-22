package org.swiggy.walletsystem.models.enums;

import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
public enum Currency {

    INR(1.00, "India"),
    USD(80.00, "USA");

    @Getter
    private double conversionRate;
    private String location;

    Currency(double v, String location) {
        this.conversionRate = v;
        this.location = location;
    }
    public static BigDecimal convert(Currency currency, BigDecimal amount) {
        return amount.multiply(BigDecimal.valueOf(currency.conversionRate)) ;
    }
    public static Currency getCurrency(String locationTo) {
        for(Currency currency : Currency.values()) {
            if(currency.location.equals(locationTo)) {
                return currency;
            }
        }
        return null;

    }
}
