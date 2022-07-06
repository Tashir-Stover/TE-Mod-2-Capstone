package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.AccountNotFoundException;
import com.techelevator.tenmo.model.TenmoAccount;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TenmoAccount findAccountById(int id){
        String sql = "SELECT * FROM account WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if (results.next()) {
            return mapRowToAccount(results);
        } else {
            throw new AccountNotFoundException();
        }

    }

    @Override
    public TenmoAccount findAccountByUsername(String username){
        String sql = "SELECT * FROM account " +
                "JOIN tenmo_user ON account.user_id = tenmo_user.user_id " +
                "WHERE username = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        if (results.next()) {
            return mapRowToAccount(results);
        } else {
            throw new AccountNotFoundException();
        }
    }

    public void addToBalance( BigDecimal transferAmount, int accountTo)
    {
        String sql =
        "UPDATE account " +
        "SET balance = balance + ? " +
        "WHERE account_id = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferAmount, accountTo);

    }

    public void subtractFromBalance(BigDecimal transferAmount, int accountFrom)
    {
        String sql =
                "UPDATE account " +
                "SET balance = balance - ? " +
                 "WHERE account_id = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferAmount, accountFrom);

    }

    public void transfer(int accountToId, int accountFromID, BigDecimal transferAmount)
    {

    }

    private TenmoAccount mapRowToAccount(SqlRowSet rs) {
        TenmoAccount acc = new TenmoAccount();
        acc.setAccountId(rs.getInt("user_id"));
        acc.setBalance(rs.getBigDecimal("balance"));
        return acc;
    }

}
