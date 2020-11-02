package ru.jpixel.personaldiaryservice.exceptions;

public class CryptoFacadeException extends Exception {

    public CryptoFacadeException() {
        super();
    }
    
    public CryptoFacadeException(String msg) {
        super(msg);
    }
    
    public CryptoFacadeException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public CryptoFacadeException(Throwable cause) {
        super(cause);
    }
}
