package com.sidgs.odp.npi.error;


public class InvalidNPITypeException extends Exception{
    public InvalidNPITypeException() {
    }

    public InvalidNPITypeException(String message) {
        super(message);
    }

    public InvalidNPITypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidNPITypeException(Throwable cause) {
        super(cause);
    }

    public InvalidNPITypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
