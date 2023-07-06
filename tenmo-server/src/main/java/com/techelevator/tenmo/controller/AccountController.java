package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {
    private final AccountDao accountDao;
    public AccountController(AccountDao accountDao){this.accountDao=accountDao;}

    @GetMapping(value="/account/{userId}/balance")
    public BigDecimal getBalance(@PathVariable("userId") int userId){
        BigDecimal balance = accountDao.getBalance(userId);
        if(balance==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account does not exist.");
        }else{
            return balance;
        }
    }
    @GetMapping(value="/user")
    public List<User> getUsers(){
        return accountDao.getUsers();
    }

    @PutMapping(value="/transfer/{fromId}/{toId}/{amount}")
    public void makeTransfer(@PathVariable int fromId, @PathVariable int toId, @PathVariable double amount){
        accountDao.makeTransfer(fromId, amount, toId);
    }
    @GetMapping(value="/account/{userId}")
    public Account getAccountByUserId(@PathVariable int userId){
        return accountDao.getAccountByUserId(userId);
    }
    @GetMapping(value="/user/{accountId}")
    public User getUserByAccountId(@PathVariable int accountId){
        return accountDao.getUserByAccountId(accountId);
    }

}
