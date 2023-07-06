package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {
    BigDecimal getBalance(int userId);

    void updateBalance(int userId, BigDecimal updateAmount);

    void makeTransfer(int fromUser, double amount, int toUser);

    List<User> getUsers();
    User getUserByAccountId(int accountId);

    Account getAccountByUserId(int userId);

}
