package com.techelevator.tenmo.model;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public class TenmoTransfer {

    @NotBlank
    private int  transferId;

    @NotBlank
    private int transferTypeId;

    @NotBlank
    private int transferStatusId;

    @NotBlank
    private int accountFrom;

    @NotBlank
    private int accountTo;

    @NotBlank
    private BigDecimal amount;

    //private TransferDTO transferDTO;

    //Constructor


    public TenmoTransfer() {
        this.transferId = transferId;
        this.transferTypeId = transferTypeId;
        this.transferStatusId = transferStatusId;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }

    public TenmoTransfer(int transferId, int transferTypeId, int transferStatusId, int accountFrom, int accountTo, BigDecimal amount) {
        this.transferId = transferId;
        this.transferTypeId = transferTypeId;
        this.transferStatusId = transferStatusId;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }

//    public TenmoTransfer(int transferId, int transferTypeId, int transferStatusId, TransferDTO transferDTO) {
//        this.transferId = transferId;
//        this.transferTypeId = transferTypeId;
//        this.transferStatusId = transferStatusId;
//        this.transferDTO = transferDTO;
//    }

    //Getter and Setters
    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public int getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }



    public int getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public int getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }

    public int getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    //    public TransferDTO getTransferDTO() {
//        return transferDTO;
//    }
//
//    public void setTransferDTO(TransferDTO transferDTO) {
//        this.transferDTO = transferDTO;
//    }


}
