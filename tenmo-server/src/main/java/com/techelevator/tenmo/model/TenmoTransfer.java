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

    private TransferDTO transferDTO;



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

    public TransferDTO getTransferDTO() {
        return transferDTO;
    }

    public void setTransferDTO(TransferDTO transferDTO) {
        this.transferDTO = transferDTO;
    }




    //Constructor
    public TenmoTransfer() {

        this.transferId = transferId;
        this.transferTypeId = transferTypeId;
        this.transferStatusId = transferStatusId;
        this.transferDTO = transferDTO;


    }

}
