package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TenmoAccount;

import java.math.BigDecimal;

public interface AccountDao {

    TenmoAccount findAccountById(int id);

    TenmoAccount findAccountByUsername(String username);

    void transfer(TenmoAccount sendingAccount, TenmoAccount receivingAccount, BigDecimal transferAmount);

}
