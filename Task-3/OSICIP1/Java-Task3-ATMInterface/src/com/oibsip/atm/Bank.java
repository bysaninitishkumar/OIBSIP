package com.oibsip.atm;

import java.util.ArrayList;

public class Bank {
    private final ArrayList<BankAccount> accounts;
    private final ArrayList<Transaction> transactionHistory;
    private BankAccount currentAccount;

    public Bank() {
        accounts = new ArrayList<>();
        transactionHistory = new ArrayList<>();

        accounts.add(new BankAccount(1001, 1111, 5000.0));
        accounts.add(new BankAccount(1002, 2222, 3000.0));
        accounts.add(new BankAccount(1003, 3333, 7000.0));
    }

    public boolean authenticate(int userId, int pin) {
        for (BankAccount account : accounts) {
            if (account.getAccountId() == userId && account.getPin() == pin) {
                currentAccount = account;
                return true;
            }
        }
        return false;
    }

    public boolean deposit(double amount) {
        if (amount <= 0 || currentAccount == null) {
            return false;
        }

        currentAccount.setBalance(currentAccount.getBalance() + amount);
        transactionHistory.add(new Transaction("Deposit", amount, currentAccount.getAccountId(), -1));
        return true;
    }

    public boolean withdraw(double amount) {
        if (currentAccount == null || amount <= 0) {
            return false;
        }

        if (currentAccount.getBalance() < amount) {
            return false;
        }

        currentAccount.setBalance(currentAccount.getBalance() - amount);
        transactionHistory.add(new Transaction("Withdraw", amount, currentAccount.getAccountId(), -1));
        return true;
    }

    public boolean transfer(int recipientAccountId, double amount) {
        if (currentAccount == null || amount <= 0) {
            return false;
        }

        if (currentAccount.getBalance() < amount) {
            return false;
        }

        BankAccount recipient = findAccount(recipientAccountId);
        if (recipient == null) {
            return false;
        }

        currentAccount.setBalance(currentAccount.getBalance() - amount);
        recipient.setBalance(recipient.getBalance() + amount);
        transactionHistory.add(new Transaction("Transfer", amount, currentAccount.getAccountId(), recipientAccountId));
        return true;
    }

    public ArrayList<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public double getBalance() {
        return currentAccount != null ? currentAccount.getBalance() : 0.0;
    }

    private BankAccount findAccount(int accountId) {
        for (BankAccount account : accounts) {
            if (account.getAccountId() == accountId) {
                return account;
            }
        }
        return null;
    }
}
