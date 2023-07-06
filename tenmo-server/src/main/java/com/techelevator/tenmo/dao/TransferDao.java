package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatus;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    List<Transfer> getSentOrReceivedTransfers(int accountId);
    Transfer getTransferById(int transferId);
    List<Transfer> getPendingTransfers();
    void sendTransfer(int transferTypeId, int transferStatusId, int fromUserId, int toUserId, BigDecimal amount);
    void requestTransfer(int transferTypeId, int transferStatusId, int requesterId, int requesteeId, BigDecimal amount);
    void approveTransfer(Transfer transfer);
    void rejectTransfer(Transfer transfer);
}
