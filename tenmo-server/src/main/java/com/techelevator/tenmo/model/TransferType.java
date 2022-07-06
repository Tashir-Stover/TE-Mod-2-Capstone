package com.techelevator.tenmo.model;

import javax.validation.constraints.NotBlank;

public class TransferType {

    @NotBlank
    private int transferTypeId;
    @NotBlank
    private String transferTypeDesc;


    //transfer types are Request and Send
    public TransferType(){
        this.transferTypeId = transferTypeId;
        this.transferTypeDesc = transferTypeDesc;
    }

    public int getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public String getTransferTypeDesc() {
        return transferTypeDesc;
    }

    public void setTransferTypeDesc(String transferTypeDesc) {
        this.transferTypeDesc = transferTypeDesc;
    }
}
