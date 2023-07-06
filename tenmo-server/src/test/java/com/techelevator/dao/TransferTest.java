package com.techelevator.dao;

import com.techelevator.tenmo.model.Transfer;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class TransferTest {

    @Test
    public void transferConstructor_shouldSetValues() {

        int transferId = 1;
        int accountFrom = 123;
        int accountTo = 456;
        BigDecimal amount = BigDecimal.valueOf(100.0);
        int transferTypeId = 1;
        int transferStatusId = 2;

        Transfer transfer = new Transfer(transferId, accountFrom, accountTo, amount, transferTypeId, transferStatusId);

        assertEquals(transferId, transfer.getTransferId());
        assertEquals(accountFrom, transfer.getAccountFrom());
        assertEquals(accountTo, transfer.getAccountTo());
        assertEquals(amount, transfer.getAmount());
        assertEquals(transferTypeId, transfer.getTransferTypeId());
        assertEquals(transferStatusId, transfer.getTransferStatusId());
    }

    @Test
    public void transferSettersAndGetters_shouldWorkCorrectly() {

        Transfer transfer = new Transfer();
        int transferId = 2;
        int accountFrom = 789;
        int accountTo = 987;
        BigDecimal amount = BigDecimal.valueOf(200.0);
        int transferTypeId = 2;
        int transferStatusId = 1;

        transfer.setTransferId(transferId);
        transfer.setAccountFrom(accountFrom);
        transfer.setAccountTo(accountTo);
        transfer.setAmount(amount);
        transfer.setTransferTypeId(transferTypeId);
        transfer.setTransferStatusId(transferStatusId);

        assertEquals(transferId, transfer.getTransferId());
        assertEquals(accountFrom, transfer.getAccountFrom());
        assertEquals(accountTo, transfer.getAccountTo());
        assertEquals(amount, transfer.getAmount());
        assertEquals(transferTypeId, transfer.getTransferTypeId());
        assertEquals(transferStatusId, transfer.getTransferStatusId());
    }
}
