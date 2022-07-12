package com.techelevator.tenmo.model;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public class TransferDTO {


    private TenmoAccount senderAcct;

    private TenmoAccount receiverAcct;

    private BigDecimal amount;

    public TransferDTO(TenmoAccount senderAcct, TenmoAccount receiverAcct, BigDecimal amount) {
        this.senderAcct = senderAcct;
        this.receiverAcct = receiverAcct;
        this.amount = amount;
    }

    public TenmoAccount getSenderAcct() {
        return senderAcct;
    }

    public void setSenderAcct(TenmoAccount senderAcct) {
        this.senderAcct = senderAcct;
    }

    public TenmoAccount getReceiverAcct() {
        return receiverAcct;
    }

    public void setReceiverAcct(TenmoAccount receiverAcct) {
        this.receiverAcct = receiverAcct;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "TransferDTO{" +
                "senderAccount='" + senderAcct + '\'' +
                ", receiverAccount='" + receiverAcct + '\'' + ", amount='" + amount + '\'' +
                '}';
    }
}
