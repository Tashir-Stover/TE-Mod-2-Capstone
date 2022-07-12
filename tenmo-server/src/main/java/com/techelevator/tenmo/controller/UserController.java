package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.TenmoAccount;
import com.techelevator.tenmo.model.TenmoTransfer;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        return accountDao.findAccountByUserId(id).getBalance();
    }

    //pass through a prinicpal
    @RequestMapping(path = "/transfers", method = RequestMethod.POST)
    public void transfer(@Valid @RequestBody TransferDTO transferDTO){
        if((transferDTO.getAmount().compareTo(transferDTO.getSenderAcct().getBalance()) < 0) && transferDTO.getAmount().compareTo(new BigDecimal("0")) > 0){
            transferDAO.transfer(transferDTO);
        } else {
            System.out.println("Transfer failed -- Verify that you have enough money in your account and that the amount you are trying to send is greater than 0");
        }
    }

    @RequestMapping(path = "/transfers/{transferId}", method = RequestMethod.GET)
    public TenmoTransfer getTransferById(@PathVariable int transferId){
        return transferDAO.getTransferById(transferId);
    }



    @RequestMapping(path = "/transfers", method = RequestMethod.GET)
    public List<TenmoTransfer> getAllTransfers(Principal user){

        int userId = userDao.findIdByUsername(user.getName());

        List<TenmoTransfer> transfers = transferDAO.getAllTransfersByUserId(userId);

       return transfers;
    }

    @RequestMapping(path = "/tenmo_account/search", method = RequestMethod.GET)
    public TenmoAccount getTenmoAccount(Principal user){
        int userId = userDao.findIdByUsername(user.getName());
        return accountDao.findAccountByUserId(userId);
    }

    @RequestMapping(path = "/tenmo_account/account_id_search", method = RequestMethod.GET)
    public int getCurrentUserAccountId(Principal user){
        int userId = userDao.findIdByUsername(user.getName());
        return accountDao.findAccountByUserId(userId).getAccountId();
    }

    @RequestMapping(path = "/tenmo_account/{id}", method = RequestMethod.GET)
    public User getUserByAccountId(@PathVariable int id){
        User user = userDao.getUserByAccountId(id);
        return user;
    }

    @RequestMapping(path = "/tenmo_account/find_by_user_id/{id}", method = RequestMethod.GET)
    public TenmoAccount getAccountByUserId(@PathVariable int id){
        TenmoAccount account = accountDao.findAccountByUserId(id);
        return account;
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
