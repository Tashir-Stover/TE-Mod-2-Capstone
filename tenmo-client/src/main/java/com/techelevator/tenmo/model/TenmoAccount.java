package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class TenmoAccount {

    private int accountId;
    private int userId;
    private BigDecimal balance;

    // Getter and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
