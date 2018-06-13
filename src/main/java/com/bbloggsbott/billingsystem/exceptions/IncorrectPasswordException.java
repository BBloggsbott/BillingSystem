package com.bbloggsbott.billingsystem.exceptions;

/**
 * Exception for when an incorrect password is encountered
 */
public class IncorrectPasswordException extends Exception {
    private String userName;

    /**
     * Constructor for this exception
     */
    public IncorrectPasswordException(String userName){
        this.userName = userName;
    }

    /**
     * Override toString() method
     */
    @Override
    public String toString() {
        return "Incorrect Password for "+userName;
    }
}
