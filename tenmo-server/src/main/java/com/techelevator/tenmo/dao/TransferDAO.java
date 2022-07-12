package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.TenmoTransfer;
import com.techelevator.tenmo.model.TransferDTO;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDAO {
    void updateReceiverBalance(TransferDTO transferDTO);
    void updateSenderBalance(TransferDTO transferDTO);
    void transfer(TransferDTO transferDTO);
    TenmoTransfer getTransferById(int id);
    int getTransferTypeById(int id);
    int getTransferStatusById(int id);
    String getTransferTypeDescById(int id);
    String getTransferStatusDescById(int id);
    int getSendingAccountId(TransferDTO transferDTO);
    int getReceivingAccountId(TransferDTO transferDTO);
    List<TenmoTransfer> getAllTransfersByUserId(int userId);

}
