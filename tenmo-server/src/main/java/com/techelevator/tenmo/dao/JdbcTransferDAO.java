package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TenmoAccount;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcTransferDAO implements TransferDAO {

    private final JdbcTemplate jdbcTemplate;
    public JdbcTransferDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addToBalance(BigDecimal transferAmount, int accountTo)
    {
        String sql =
                "UPDATE tenmo_account " +
                        "SET balance = balance + ? " +
                        "WHERE account_id = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferAmount, accountTo);

    }

    public void subtractFromBalance(BigDecimal transferAmount, int accountFrom)
    {
        String sql =
                "UPDATE tenmo_account " +
                        "SET balance = balance - ? " +
                        "WHERE account_id = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferAmount, accountFrom);

    }

    public void transfer(TenmoAccount sendingAccount, TenmoAccount receivingAccount, BigDecimal transferAmount) {
        sendingAccount.setBalance(sendingAccount.getBalance().subtract(transferAmount));
        receivingAccount.setBalance(receivingAccount.getBalance().add(transferAmount));
        //add this transfer to transfer table
        String sql = "INSERT INTO transfer(transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES(2, 2, ?, ?, ?);";
        jdbcTemplate.queryForRowSet(sql, sendingAccount.getAccountId(), receivingAccount.getAccountId(), transferAmount);
        //add transfer_type table
        sql = "INSERT INTO transfer_type(transfer_type_desc) VALUES ('Send');";
        jdbcTemplate.queryForRowSet(sql);
        //add transfer transfer_status table
        sql = "INSERT INTO transfer_status(transfer_status_desc) VALUES('Approved');";
        jdbcTemplate.queryForRowSet(sql);
    }
}
