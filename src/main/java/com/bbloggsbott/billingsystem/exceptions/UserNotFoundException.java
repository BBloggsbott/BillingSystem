package com.bbloggsbott.billingsystem.exceptions;

/**
 * Exception for when User is not found
 */
public class UserNotFoundException extends Exception{
    private String userName;

    /**
     * Constructor for this exception
     */
    public UserNotFoundException(String userName){
        this.userName = userName;
    }

    /**
     * Override toString() method
     */
    @Override
    public String toString() {
        return "User: "+userName+" not found.";
    }
}
