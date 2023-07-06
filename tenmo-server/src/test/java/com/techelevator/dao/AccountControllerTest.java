package com.techelevator.dao;

import com.techelevator.tenmo.controller.AccountController;
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

public class AccountControllerTest {
    @Mock
    private AccountDao accountDao;
    private AccountController accountController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        accountController = new AccountController(accountDao);
    }

    @Test
    public void getBalance_returnsBalance_whenAccountExists() {
        int userId = 123;
        BigDecimal balance = new BigDecimal("1000.00");

        when(accountDao.getBalance(userId)).thenReturn(balance);

        BigDecimal result = accountController.getBalance(userId);

        assertEquals(balance, result);
        verify(accountDao).getBalance(userId);
    }

    @Test
    public void makeTransfer_callsAccountDao() {
        int fromId = 1;
        int toId = 2;
        double amount = 100.0;

        accountController.makeTransfer(fromId, toId, amount);

        verify(accountDao).makeTransfer(fromId, amount, toId);
    }
    @Test
    public void getAccountByUserId_returnsAccountWithMatchingUserId() {
        int userId = 123;
        Account expectedAccount = new Account();
        expectedAccount.setAccountId(1);
        expectedAccount.setUserId(userId);
        expectedAccount.setAccountBalance(BigDecimal.valueOf(1000.0));

        when(accountDao.getAccountByUserId(userId)).thenReturn(expectedAccount);

        Account result = accountController.getAccountByUserId(userId);

        assertEquals(expectedAccount, result);
        verify(accountDao).getAccountByUserId(userId);
    }

    @Test
    public void getUsers_returnsListOfUsers() {
        List<User> expectedUsers = new ArrayList<>();

        when(accountDao.getUsers()).thenReturn(expectedUsers);

        List<User> users = accountController.getUsers();

        assertEquals(expectedUsers, users);
        verify(accountDao).getUsers();
    }

}
