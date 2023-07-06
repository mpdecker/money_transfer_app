package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import java.math.BigDecimal;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {
    private final TransferDao transferDao;
    public TransferController(TransferDao transferDao){this.transferDao=transferDao;}

    @GetMapping(value="/transfer/{transferId}")
    public Transfer getTransferById(@PathVariable int transferId){
        return transferDao.getTransferById(transferId);
    }
    @GetMapping(value="/transfers/{accountFrom}")
    public List<Transfer> getSentOrReceivedTransfers(@PathVariable int accountFrom){
        return transferDao.getSentOrReceivedTransfers(accountFrom);
    }
    @GetMapping(value="/transfers/pending")
    public List<Transfer> getPendingTransfers(){
        return transferDao.getPendingTransfers();
    }

    @PostMapping(value="/transfer")
    public void sendTransfer(@RequestBody TransferDto transferDto){
        int fromUserId = transferDto.getFromUserId();
        int toUserId = transferDto.getToUserId();
        BigDecimal amount = transferDto.getAmount();
        transferDao.sendTransfer(transferDto.getTransferTypeId(), transferDto.getTransferStatusId(), fromUserId, toUserId, amount);
    }


}
