package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.TenmoAccount;
import com.techelevator.tenmo.model.User;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/users")
@PreAuthorize("isAuthenticated()")
public class UserController {

    private UserDao userDao;
    private AccountDao accountDao;

    public UserController(UserDao userDao, AccountDao accountDao){
        this.userDao = userDao;
        this.accountDao = accountDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getAllUsers(){
        return userDao.findAll();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable int id){
        return userDao.getUserById(id);
    }

    // Display Account Balance
    @RequestMapping(path = "{id}/tenmo_account", method = RequestMethod.GET)
    public TenmoAccount displayBalance(@PathVariable int id){
        return accountDao.findAccountById(id);
    }

    public void transfer(){

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
