package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDto;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

public class TransferService {
    private final String BASE_URL ="http://localhost:8080/";
    private final AuthenticatedUser authenticatedUser;
    private final String token;
    private final int userId;

    private final RestTemplate restTemplate = new RestTemplate();

    public TransferService(AuthenticatedUser authenticatedUser){
        this.authenticatedUser=authenticatedUser;
        this.token=authenticatedUser.getToken();
        this.userId=authenticatedUser.getUser().getId();

    }
    public Transfer getTransferById(int transferId){
        HttpEntity<?> entity = makeAuthEntity();
        String url = BASE_URL+"/transfer/"+transferId;
        ResponseEntity<Transfer> response;
        try{
            response = restTemplate.exchange(url, HttpMethod.GET, entity, Transfer.class);
        }catch(RestClientException e){
            throw e;
        }
        return response.getBody();
    }
    public List<Transfer> getSentOrReceivedTransfers(int accountId){
        HttpEntity<?> entity = makeAuthEntity();
        String url = BASE_URL+"/transfers/"+accountId;
        try{
            ResponseEntity<Transfer[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Transfer[].class);
            if(response.getStatusCode()== HttpStatus.OK){
                return List.of(response.getBody());
            }else{
                throw new RuntimeException("Failed to return transfers: "+response.getStatusCodeValue());
            }
        }catch(RestClientException e){
            throw new RuntimeException("Failed to return transfers: "+e.getMessage());
        }
    }
    public List<Transfer> getPendingTransfers(){
        HttpEntity<?> entity = makeAuthEntity();
        String url = BASE_URL+"/transfers/pending";
        try{
            ResponseEntity<Transfer[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Transfer[].class);
            if(response.getStatusCode()==HttpStatus.OK){
                return List.of(response.getBody());
            }else{
                throw new RuntimeException("Failed to return pending transfers: "+response.getStatusCodeValue());
            }
        }catch(RestClientException e){
            throw new RuntimeException("Failed to return pending transfers: "+e.getMessage());
        }
    }
    public void sendTransfer(TransferDto transferDto){
        HttpEntity<TransferDto> entity = makeTransferDtoEntity(transferDto);
        String url = BASE_URL+"/transfer";
        try {
            restTemplate.exchange(url, HttpMethod.POST, entity, TransferDto.class);
        }catch (RestClientResponseException | ResourceAccessException e) {
            System.out.println("Error writing transfer"+e.getMessage());
            BasicLogger.log(e.getMessage());

        }


    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return new HttpEntity<>(headers);
    }
    private HttpEntity<TransferDto> makeTransferDtoEntity(TransferDto transferDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        return new HttpEntity<TransferDto>(transferDto, headers);
    }
}
