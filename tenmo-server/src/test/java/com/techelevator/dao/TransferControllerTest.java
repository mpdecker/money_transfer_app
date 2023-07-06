package com.techelevator.dao;

import com.techelevator.tenmo.controller.TransferController;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.TransferDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;

public class TransferControllerTest {
    @Mock
    private TransferDao transferDao;

    private TransferController transferController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        transferController = new TransferController(transferDao);
    }

    @Test
    public void sendTransfer_shouldCallTransferDaoWithCorrectParameters() {
        TransferDto transferDto = new TransferDto();
        transferDto.setFromUserId(1);
        transferDto.setToUserId(2);
        transferDto.setAmount(new BigDecimal("100.00"));
        transferDto.setTransferTypeId(1);
        transferDto.setTransferStatusId(2);

        transferController.sendTransfer(transferDto);

        verify(transferDao).sendTransfer(1, 2, 1, 2, new BigDecimal("100.00"));
    }
}
