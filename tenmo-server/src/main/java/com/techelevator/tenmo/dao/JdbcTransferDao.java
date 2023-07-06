package com.techelevator.tenmo.dao;

import java.util.ArrayList;
import java.util.List;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component
public class JdbcTransferDao implements TransferDao{
    private final JdbcTemplate jdbcTemplate;
    public JdbcTransferDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    public TransferStatus getTransferStatus(){
        TransferStatus status = null;

        return status;
    }
    @Override
    public Transfer getTransferById(int transferId){
        Transfer transfer = null;
        String sql = "SELECT * FROM transfer WHERE transfer_id = ?;";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, transferId);
        if(rs.next()){
            transfer = mapRowToTransfer(rs);
        }

        return transfer;
    }
    @Override
    public List<Transfer> getSentOrReceivedTransfers(int accountId){
      List<Transfer> transfers = new ArrayList<>();
      String sql = "SELECT * FROM transfer WHERE account_from = ? OR account_to = ?;";
      SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, accountId, accountId);
      while(rs.next()){
          transfers.add(mapRowToTransfer(rs));
      }
      return transfers;
    }
    @Override
    public List<Transfer> getPendingTransfers(){
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT * FROM transfer WHERE transfer_status_id = 1;";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
        while(rs.next()){
            transfers.add(mapRowToTransfer(rs));
        }
        return transfers;
    }
    @Override
    public void sendTransfer(int transferTypeId, int transferStatusId, int fromUserId, int toUserId, BigDecimal amount){
        if(fromUserId==toUserId){
            throw new RuntimeException("Cannot send/request money from yourself.");
        }
        if(amount.doubleValue()<=0){
            throw new RuntimeException("Must send/request a value greater than 0.");
        }
        String sql = "INSERT INTO transfer (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, " +
                "amount) VALUES (DEFAULT, ?, ?, (SELECT account_id FROM account WHERE user_id = ?), (SELECT account_id " +
                "FROM account WHERE user_id=?), ?);";
        jdbcTemplate.update(sql, transferTypeId, transferStatusId, fromUserId, toUserId, amount.doubleValue());
    }
    @Override
    public void requestTransfer(int transferTypeId, int transferStatusId, int requesterId, int requesteeId, BigDecimal amount){
        if(requesterId==requesteeId){
            throw new RuntimeException("Cannot request money from yourself.");
        }
        if(amount.doubleValue()<=0){
            throw new RuntimeException("Must request a value greater than 0.");
        }
        String sql = "INSERT INTO transfer (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, " +
                "amount) VALUES (DEFAULT, ?, ?, (SELECT account_id FROM account WHERE user_id = ?), (SELECT account_id " +
                "FROM account WHERE user_id=?), ?);";
        jdbcTemplate.update(sql, transferTypeId, transferStatusId, requesterId, requesteeId, amount.doubleValue());

    }
    @Override
    public void approveTransfer(Transfer pendingTransfer){

    }
    @Override
    public void rejectTransfer(Transfer pendingTransfer){

    }

    private Transfer mapRowToTransfer(SqlRowSet rs){
        Transfer transfer = new Transfer();
        transfer.setTransferId(rs.getInt("transfer_id"));
        transfer.setTransferTypeId(rs.getInt("transfer_type_id"));
        transfer.setTransferStatusId(rs.getInt("transfer_status_id"));
        transfer.setAccountFrom(rs.getInt("account_from"));
        transfer.setAccountTo(rs.getInt("account_to"));
        transfer.setAmount(rs.getBigDecimal("amount"));
        return transfer;
    }
}
