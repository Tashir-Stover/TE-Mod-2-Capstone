package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.TenmoTransfer;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.security.Principal;

public class UserService {

    public static final String API_BASE_URL = "http://localhost:8080/users";
    private RestTemplate restTemplate = new RestTemplate();

    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public User getUser(int id){

        User user = null;
        try {
            // Add code here to send the request to the API and get the auction from the response.
            ResponseEntity<User> response = restTemplate.exchange(API_BASE_URL + id, HttpMethod.GET, makeAuthEntity(),User.class);
            user = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return user;

    }

    public User[] getAllUsers(){
        User[] users = null;
        try {
            ResponseEntity<User[]> response =
                    restTemplate.exchange(API_BASE_URL, HttpMethod.GET, makeAuthEntity(), User[].class);
            users = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return users;
    }

    //get transfer by id

    public TenmoTransfer getTransferById(int id){
        TenmoTransfer transfer = null;
        try {
            // Add code here to send the request to the API and get the auction from the response.
            ResponseEntity<TenmoTransfer> response = restTemplate.exchange(API_BASE_URL + "/transfers/" + id, HttpMethod.GET, makeAuthEntity(),TenmoTransfer.class);
            transfer = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfer;
    }

    //get all transfers
    public TenmoTransfer[] getAllTransfersByUser(){
        TenmoTransfer[] transfers = null;
        try {
            ResponseEntity<TenmoTransfer[]> response =
                    restTemplate.exchange(API_BASE_URL + "/transfers", HttpMethod.GET, makeAuthEntity(), TenmoTransfer[].class);
            transfers = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }

    public boolean transfer(TransferDTO transferDTO){
        boolean success = false;
        try{
            restTemplate.put(API_BASE_URL + "/transfers", HttpMethod.POST, makeAuthEntity(), Void.class);
            success = true;
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return success;
    }

    public BigDecimal getBalance(){
        BigDecimal balance = new BigDecimal("0");
        try{
            ResponseEntity<BigDecimal> response =
            restTemplate.exchange(API_BASE_URL + "/view_balance", HttpMethod.GET, makeAuthEntity(), BigDecimal.class);
            balance = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return balance;
    }

    public TransferDTO getTransferDTO(){

    }

    private HttpEntity<User> makeUserEntity(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(user, headers);
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
