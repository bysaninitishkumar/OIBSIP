package com.oibsip.atm;

public class BankAccount {
    private int accountId;
    private int pin;
    private double balance;

    public BankAccount(int accountId, int pin, double balance) {
        this.accountId = accountId;
        this.pin = pin;
        this.balance = balance;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
