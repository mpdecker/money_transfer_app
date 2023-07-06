package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {
    private int transferId;
    private int accountFrom;
    private int accountTo;
    private BigDecimal amount;
    private int transferTypeId;
    private int transferStatusId;

    public Transfer(){}
    public Transfer(int transferId, int accountFrom, int accountTo, BigDecimal amount, int transferTypeId, int transferStatusId){
        this.transferId=transferId;
        this.accountFrom=accountFrom;
        this.accountTo=accountTo;
        this.amount=amount;
        this.transferTypeId=transferTypeId;
        this.transferStatusId=transferStatusId;
    }




    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
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

    public int getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public int getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }
}
