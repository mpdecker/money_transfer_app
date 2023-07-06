package com.techelevator.dao;

import com.techelevator.tenmo.model.Account;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {

    @Test
    public void getAccountIdReturnsCorrectValue() {
        int accountId = 123;
        Account account = new Account();
        account.setAccountId(accountId);

        assertEquals(accountId, account.getAccountId());
    }

    @Test
    public void getUserIdReturnsCorrectValue() {
        int userId = 456;
        Account account = new Account();
        account.setUserId(userId);

        assertEquals(userId, account.getUserId());
    }
    @Test
    public void getAccountBalanceCorrectValue() {
        BigDecimal balance = new BigDecimal("1000.00");
        Account account = new Account();
        account.setAccountBalance(balance);

        assertEquals(balance, account.getAccountBalance());
    }
    @Test
    public void setAccountBalanceSetsCorrectValue() {
        BigDecimal balance = new BigDecimal("1500.00");
        Account account = new Account();
        account.setAccountBalance(balance);

        assertEquals(balance, account.getAccountBalance());
    }
}
