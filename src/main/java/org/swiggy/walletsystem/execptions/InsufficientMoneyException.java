package org.swiggy.walletsystem.execptions;

public class InsufficientMoneyException extends Exception {
    public InsufficientMoneyException(String message) {
        super(message);
    }
}
