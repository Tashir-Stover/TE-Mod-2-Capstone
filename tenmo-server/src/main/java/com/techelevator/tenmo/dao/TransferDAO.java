package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TenmoAccount;

import java.math.BigDecimal;

public interface TransferDAO {
    void addToBalance(BigDecimal transferAmount, int accountTo);
    void subtractFromBalance(BigDecimal transferAmount, int accountFrom);
    void transfer(TenmoAccount sendingAccount, TenmoAccount receivingAccount, BigDecimal transferAmount);
}
