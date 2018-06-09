package com.bbloggsbott.billingsystem.exceptions;

public class IncorrectPasswordException extends Exception {
    private String userName;

    public IncorrectPasswordException(String userName){
        this.userName = userName;
    }
    @Override
    public String toString() {
        return "Incorrect Password for "+userName;
    }
}
