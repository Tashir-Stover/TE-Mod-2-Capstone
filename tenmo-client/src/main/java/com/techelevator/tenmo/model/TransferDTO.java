package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class TransferDTO {
    private TenmoAccount senderAcct;
    private TenmoAccount receiverAcct;
    private BigDecimal amount;

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
