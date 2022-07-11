package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TenmoAccount;

public interface AccountDao {

    TenmoAccount findAccountByUserId(int id);

    TenmoAccount findAccountByUsername(String username);



}
