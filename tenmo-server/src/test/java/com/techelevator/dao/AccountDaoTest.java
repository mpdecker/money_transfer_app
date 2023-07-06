package com.techelevator.dao;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccountDaoTest {
    @Mock
    private AccountDao accountDao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void getBalanceReturnForExistingUser() {
        int userId = 123;
        BigDecimal expectedBalance = BigDecimal.valueOf(1000.0);

        when(accountDao.getBalance(userId)).thenReturn(expectedBalance);

        BigDecimal balance = accountDao.getBalance(userId);

        assertEquals(expectedBalance, balance);
        verify(accountDao).getBalance(userId);
    }

    @Test
    public void makeTransferBetweenUsers() {
        int fromUser = 123;
        int toUser = 456;
        double amount = 100.0;

        accountDao.makeTransfer(fromUser, amount, toUser);
        verify(accountDao).makeTransfer(fromUser, amount, toUser);
    }
    @Test
    public void getUsers_returnsListOfUsers() {
        List<User> expectedUsers = new ArrayList<>();
                when(accountDao.getUsers()).thenReturn(expectedUsers);

        List<User> users = accountDao.getUsers();

        assertEquals(expectedUsers, users);
        verify(accountDao).getUsers();
    }

    @Test
    public void getAccountByUserId_returnsAccount_forExistingUser() {
        int userId = 123;
        Account expectedAccount = new Account();

                when(accountDao.getAccountByUserId(userId)).thenReturn(expectedAccount);

        Account account = accountDao.getAccountByUserId(userId);

        assertEquals(expectedAccount, account);
        verify(accountDao).getAccountByUserId(userId);
    }
}

