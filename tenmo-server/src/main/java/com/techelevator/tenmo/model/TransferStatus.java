package com.techelevator.tenmo.model;

import javax.validation.constraints.NotBlank;

public class TransferStatus {
    @NotBlank
    private int transferStatusId;
    @NotBlank
    private String transferStatusDesc;


    //transfer statuses are Pending, Approved, and Rejected
    public TransferStatus(){
        this.transferStatusId = transferStatusId;
        this.transferStatusDesc = transferStatusDesc;
    }

    public int getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public String getTransferStatusDesc() {
        return transferStatusDesc;
    }

    public void setTransferStatusDesc(String transferStatusDesc) {
        this.transferStatusDesc = transferStatusDesc;
    }
}
