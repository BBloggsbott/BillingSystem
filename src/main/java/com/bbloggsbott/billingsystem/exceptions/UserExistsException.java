package com.bbloggsbott.billingsystem.exceptions;

/**
 * Exception for when a User already exists
 */
public class UserExistsException extends Exception {
    private String userName;

    /**
     * Constructor for this exception
     */
    public UserExistsException(String userName){
        this.userName = userName;
    }

    /**
     * Override toString() method
     */
    @Override
    public String toString() {
        return "User "+userName+" already exists";
    }
}
