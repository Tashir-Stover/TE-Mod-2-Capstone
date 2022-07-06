package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.TenmoAccount;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@PreAuthorize("isAuthenticated()")
public class UserController {

    private UserDao userDao;

    public UserController(UserDao userDao){
        this.userDao = userDao;
    }

    // Display Account Balance

    @RequestMapping(path = "/account", method = RequestMethod.GET)
    public void displayBalance(TenmoAccount account){

        System.out.println("Your current account balance: " + account.getBalance());
    }

    //Send transfer to another user
    // Choose from list of users (excluding sender)
    //Includes user ids from to and from and amounts
    // Sender decrease by amount sent
    // Receiver increase by amount sent
    //Check balance
    //If amountsent > balance or amountsent <=0, balance remains same
    //has initial status of approved



    //Display Transfer Info By User
    //Display Transfer info by TransferId


}
