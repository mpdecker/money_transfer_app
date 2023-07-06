package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

public class AccountService {
    private final String BASE_URL ="http://localhost:8080/";
    private final AuthenticatedUser authenticatedUser;
    private final String token;
    private final int userId;

    private final RestTemplate restTemplate = new RestTemplate();

    public AccountService(AuthenticatedUser authUser){
        this.authenticatedUser=authUser;
        this.userId=authUser.getUser().getId();
        this.token= authUser.getToken();
    }

    public BigDecimal getBalance(){
        HttpEntity<?> entity = makeAuthEntity();
        String url = BASE_URL+"account/"+userId+"/balance";
        ResponseEntity<BigDecimal> response;
        try{
            response = restTemplate.exchange(url, HttpMethod.GET, entity, BigDecimal.class);
        }catch(RestClientException e){
            throw e;
        }
        return response.getBody();
    }
    public void updateBalance(int userId, BigDecimal amount){
        try{
            restTemplate.put(BASE_URL+"account/"+userId+"/balance", makeAuthEntity());
        }catch(RestClientResponseException | ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
    }
    public void makeTransfer(int fromId, BigDecimal amount, int toId){
        try{
            restTemplate.put(BASE_URL+"transfer/"+fromId+"/"+toId+"/"+amount.doubleValue(), makeAuthEntity());
        }catch(RestClientResponseException | ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        String url = BASE_URL + "user";
        HttpEntity<?> entity = makeAuthEntity();
        try {
            ResponseEntity<User[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, User[].class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return List.of(response.getBody());
            } else {
                throw new RuntimeException("Failed to retrieve users. Status code: " + response.getStatusCodeValue());
            }
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to retrieve users: " + e.getMessage());
        }
    }

    public Account getAccount(int userId){
        HttpEntity<?> entity = makeAuthEntity();
        String url = BASE_URL+"/account/"+userId;
        ResponseEntity<Account> response;
        try{
            response = restTemplate.exchange(url, HttpMethod.GET, entity, Account.class);
        }catch(RestClientException e){
            throw e;
        }
        return response.getBody();
    }
    public User getUserByAccountId(int accountId){
        HttpEntity<?> entity = makeAuthEntity();
        String url = BASE_URL+"/user/"+accountId;
        ResponseEntity<User> response;
        try{
            response = restTemplate.exchange(url, HttpMethod.GET, entity, User.class);
        }catch(RestClientException e){
            throw e;
        }
        return response.getBody();
    }



    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return new HttpEntity<>(headers);
    }
  /*  private HttpEntity<Account> makeAccountEntity(Account account){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        return new HttpEntity<>(account, headers);
    } */
}
