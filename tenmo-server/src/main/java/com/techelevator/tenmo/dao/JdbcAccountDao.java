package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao {
    private final JdbcTemplate jdbcTemplate;
    public JdbcAccountDao(JdbcTemplate jdbcTemplate){this.jdbcTemplate=jdbcTemplate;}
    @Override
    public BigDecimal getBalance(int userId){
        BigDecimal balance=null;
        String sql = "SELECT balance FROM account JOIN tenmo_user ON account.user_id = tenmo_user.user_id WHERE account.user_id = ?;";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, userId);
        if (rs.next()){
            balance=rs.getBigDecimal("balance");
        }
        return balance;
    }
    @Override
    public List<User> getUsers(){
        List<User> users = new ArrayList<>();
        String sql = "SELECT * from tenmo_user;";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
        while(rs.next()){
            users.add(mapRowToUser(rs));
            users.
        }
        return users;
    }
    @Override
    public void updateBalance(int userId, BigDecimal updateAmount){
        String sql = "UPDATE account SET balance = ? WHERE user_id = ?;";
        jdbcTemplate.update(sql, updateAmount, userId);
    }
    @Override
    public void makeTransfer(int fromUser, double amount, int toUser){
        if(fromUser==toUser){
            throw new RuntimeException("Cannot send money to yourself.");
        }
        if(amount<=0){
            throw new RuntimeException("Must send an amount greater than 0.");
        }
        if(getBalance(fromUser).doubleValue()<amount){
            throw new RuntimeException("Insufficient funds to make transfer.");
        }
        BigDecimal incrementedBalance = getBalance(toUser).add(new BigDecimal(amount));
        BigDecimal decrementedBalance = getBalance(fromUser).subtract(new BigDecimal(amount));
        updateBalance(fromUser, decrementedBalance);
        updateBalance(toUser, incrementedBalance);
    }
    @Override
    public Account getAccountByUserId(int userId){
        String sql = "SELECT * FROM account WHERE user_id = ?;";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, userId);
        if(rs.next()){
            return mapRowToAccount(rs);
        }else{
            throw new RuntimeException("Account does not exist");
        }
    }
    @Override
    public User getUserByAccountId(int accountId){
        String sql = "SELECT * FROM tenmo_user JOIN account ON tenmo_user.user_id " +
                "= account.user_id WHERE account.account_id = ?;";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, accountId);
        if(rs.next()){
            return mapRowToUser(rs);
        }else{
            throw new RuntimeException("User not found.");
        }
    }

    public User mapRowToUser(SqlRowSet rs){
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));

        return user;
    }
    public Account mapRowToAccount(SqlRowSet rs){
        Account account = new Account();
        account.setAccountId(rs.getInt("account_id"));
        account.setUserId(rs.getInt("user_id"));
        account.setAccountBalance(rs.getBigDecimal("balance"));
        return account;
    }
}
