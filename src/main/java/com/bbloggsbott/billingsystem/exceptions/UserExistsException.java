package com.bbloggsbott.billingsystem.exceptions;

public class UserExistsException extends Exception {
    private String userName;

    public UserExistsException(String userName){
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User "+userName+" already exists";
    }
}
