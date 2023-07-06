package com.techelevator.dao;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TransferDaoTest {

    private TransferDao transferDao;

    @BeforeEach
    public void setup() {
        transferDao = Mockito.mock(TransferDao.class);
    }

    @Test
    public void getSentOrReceivedTransfers_returnsListOfTransfers() {

        int accountId = 123;
        List<Transfer> transfers = new ArrayList<>();
        transfers.add(new Transfer());
        transfers.add(new Transfer());
        when(transferDao.getSentOrReceivedTransfers(accountId)).thenReturn(transfers);

        List<Transfer> result = transferDao.getSentOrReceivedTransfers(accountId);

        assertEquals(2, result.size());
    }

    @Test
    public void getTransferById_returnsTransfer() {

        int transferId = 456;
        Transfer transfer = new Transfer();
        transfer.setTransferId(transferId);
        when(transferDao.getTransferById(transferId)).thenReturn(transfer);

        Transfer result = transferDao.getTransferById(transferId);

        assertEquals(transferId, result.getTransferId());
    }

    @Test
    public void getPendingTransfers_returnsListOfTransfers() {

        List<Transfer> transfers = new ArrayList<>();
        transfers.add(new Transfer());
        transfers.add(new Transfer());
        when(transferDao.getPendingTransfers()).thenReturn(transfers);

        List<Transfer> result = transferDao.getPendingTransfers();

        assertEquals(2, result.size());
    }
}