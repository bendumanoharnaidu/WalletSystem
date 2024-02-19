package org.swiggy.walletsystem.execptions;

public class UserAlreadyPresentException extends Exception {
    public UserAlreadyPresentException(String message) {
        super(message);
    }
}
