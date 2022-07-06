package com.techelevator.tenmo.model;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public class TenmoAccount {

    @NotBlank
    private int accountId;
    @NotBlank
    private int userId;
    @NotBlank
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

    //Constructor

    public TenmoAccount(){

        this.userId = userId;
        this.accountId = accountId;
        this.balance = balance;
    }

}
