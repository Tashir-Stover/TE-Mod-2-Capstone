package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TenmoAccount;
import com.techelevator.tenmo.model.TenmoTransfer;

import java.math.BigDecimal;

public interface TransferDAO {
    void addToBalance(BigDecimal transferAmount, int accountTo);
    void subtractFromBalance(BigDecimal transferAmount, int accountFrom);
    void transfer(TenmoAccount sendingAccount, TenmoAccount receivingAccount, BigDecimal transferAmount);
    TenmoTransfer getTransferById(int id);
    int getTransferTypeById(int id);
    int getTransferStatusById(int id);
    String getTransferTypeDescById(int id);
    String getTransferStatusDescById(int id);
    int getSendingAccountId(int id);
    int getReceivingAccountId(int id);
    int getTransferId();
}
