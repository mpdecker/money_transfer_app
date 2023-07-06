package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class JdbcTransferDaoTest {
    @Mock
    private JdbcTemplate jdbcTemplate;

    private JdbcTransferDao transferDao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        transferDao = new JdbcTransferDao(jdbcTemplate);
    }

    @Test
    public void getTransferByIdShouldReturnTransfer() {

        int transferId = 1;
        SqlRowSet rowSet = mock(SqlRowSet.class);
        when(jdbcTemplate.queryForRowSet(anyString(), anyInt())).thenReturn(rowSet);
        when(rowSet.next()).thenReturn(true);
        when(rowSet.getInt("transfer_id")).thenReturn(transferId);

        Transfer transfer = transferDao.getTransferById(transferId);

        assertEquals(transferId, transfer.getTransferId());
        verify(jdbcTemplate).queryForRowSet("SELECT * FROM transfer WHERE transfer_id = ?;", transferId);
    }

    @Test
    public void getSentOrReceivedTransfersShouldReturnTransfers() {

        int accountId = 1;
        SqlRowSet rowSet = mock(SqlRowSet.class);
        when(jdbcTemplate.queryForRowSet(anyString(), anyInt(), anyInt())).thenReturn(rowSet);
        when(rowSet.next()).thenReturn(true, false);

        List<Transfer> transfers = transferDao.getSentOrReceivedTransfers(accountId);

        assertEquals(1, transfers.size());
        verify(jdbcTemplate).queryForRowSet("SELECT * FROM transfer WHERE account_from = ? OR account_to = ?;",
                accountId, accountId);
    }

    @Test
    public void getPendingTransfersShouldReturnTransfers() {

        SqlRowSet rowSet = mock(SqlRowSet.class);
        when(jdbcTemplate.queryForRowSet(anyString())).thenReturn(rowSet);
        when(rowSet.next()).thenReturn(true, false);

        List<Transfer> transfers = transferDao.getPendingTransfers();

        assertEquals(1, transfers.size());
        verify(jdbcTemplate).queryForRowSet("SELECT * FROM transfer WHERE transfer_status_id = 1;");
    }

    @Test
    public void sendTransfer_shouldInsertTransferRecord() {
        int transferTypeId = 2;
        int transferStatusId = 1;
        int fromUserId = 1;
        int toUserId = 2;
        BigDecimal amount = new BigDecimal("100.00");

        transferDao.sendTransfer(transferTypeId, transferStatusId, fromUserId, toUserId, amount);

        verify(jdbcTemplate).update("INSERT INTO transfer (transfer_id, transfer_type_id, transfer_status_id, " +
                        "account_from, account_to, amount) VALUES (DEFAULT, ?, ?, (SELECT account_id FROM account " +
                        "WHERE user_id = ?), (SELECT account_id FROM account WHERE user_id=?), ?);",
                transferTypeId, transferStatusId, fromUserId, toUserId, amount.doubleValue());
    }
    @Test
    public void requestTransferShouldInsertTransferRecord() {
        int transferTypeId = 1;
        int transferStatusId = 2;
        int requesterId = 1;
        int requesteeId = 2;
        BigDecimal amount = new BigDecimal("50.00");

        transferDao.requestTransfer(transferTypeId, transferStatusId, requesterId, requesteeId, amount);

        verify(jdbcTemplate).update("INSERT INTO transfer (transfer_id, transfer_type_id, transfer_status_id, " +
                        "account_from, account_to, amount) VALUES (DEFAULT, ?, ?, (SELECT account_id FROM account " +
                        "WHERE user_id = ?), (SELECT account_id FROM account WHERE user_id=?), ?);",
                transferTypeId, transferStatusId, requesterId, requesteeId, amount.doubleValue());
    }
}