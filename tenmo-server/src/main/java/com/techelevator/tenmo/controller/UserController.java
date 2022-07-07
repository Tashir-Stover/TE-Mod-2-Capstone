package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.TenmoAccount;
import com.techelevator.tenmo.model.User;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
@PreAuthorize("isAuthenticated()")
public class UserController {

    private UserDao userDao;
    private AccountDao accountDao;
    private TransferDAO transferDAO;

    public UserController(UserDao userDao, AccountDao accountDao, TransferDAO transferDAO){
        this.userDao = userDao;
        this.accountDao = accountDao;
        this.transferDAO = transferDAO;
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
    @RequestMapping(path = "/view_balance", method = RequestMethod.GET)
    public BigDecimal displayBalance(Principal user){
        int id = userDao.findIdByUsername(user.getName());
        return accountDao.findAccountById(id).getBalance();
    }

    @RequestMapping(path = "/transfers", method = RequestMethod.POST)
    public void transfer(@RequestBody TenmoAccount sendingAccount, @RequestBody TenmoAccount receivingAccount, @RequestBody BigDecimal transferAmount){
        if((transferAmount.compareTo(sendingAccount.getBalance()) < 0) && transferAmount.compareTo(new BigDecimal("0")) > 0){
            transfer(sendingAccount, receivingAccount, transferAmount);
        } else {
            System.out.println("Transfer failed -- Verify that you have enough money in your account and that the amount you are trying to send is greater than 0");
        }
    }

    @RequestMapping(path = "/{id}/tenmo_account/search", method = RequestMethod.GET)
    public TenmoAccount getTenmoAccount(@PathVariable int id){
        return accountDao.findAccountById(id);
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
