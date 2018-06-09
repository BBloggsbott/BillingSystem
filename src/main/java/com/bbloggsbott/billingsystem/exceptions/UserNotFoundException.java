package com.bbloggsbott.billingsystem.exceptions;

public class UserNotFoundException extends Exception{
    private String userName;

    public UserNotFoundException(String userName){
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User: "+userName+" not found.";
    }
}
