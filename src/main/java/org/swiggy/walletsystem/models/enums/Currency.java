package org.swiggy.walletsystem.models.enums;

import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum Currency {

    INR(80, "INDIA"),
    USD(1, "USA");

    @Getter
    private double conversionRate;
    private String location;

    Currency(double v, String location) {
        this.conversionRate = v;
        this.location = location;
    }
    public static BigDecimal convert(Currency currency, BigDecimal amount) {
        return amount.divide(BigDecimal.valueOf(currency.conversionRate),4, RoundingMode.HALF_UP) ;
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
