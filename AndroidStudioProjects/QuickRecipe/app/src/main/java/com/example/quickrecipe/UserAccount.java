package com.example.quickrecipe;

/**
 * Created by Gerald on 4/15/2018.
 */

public class UserAccount {

    private String firstName, username, password;

    public UserAccount(){

    }

    public UserAccount(String firstName, String username, String password){
        this.firstName = firstName;
        this.username = username;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
