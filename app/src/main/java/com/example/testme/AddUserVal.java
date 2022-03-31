package com.example.testme;

public class AddUserVal {
    String Name , Email ,Password,Number;

    public AddUserVal(String Name, String Email, String Password, String Number) {
        this.Name = Name;
        this.Email = Email;
        this.Password = Password;
        this.Number = Number;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }
}
