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
    public TenmoAccount findAccountByUserId(int id){
        String sql = "SELECT * FROM tenmo_account WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if (results.next()) {
            return mapRowToAccount(results);
        } else {
            throw new AccountNotFoundException();
        }

    }

    @Override
    public TenmoAccount findAccountByUsername(String username){
        String sql = "SELECT account_id, user_id, balance FROM tenmo_account " +
                "JOIN tenmo_user ON account.user_id = tenmo_user.user_id " +
                "WHERE username = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        if (results.next()) {
            return mapRowToAccount(results);
        } else {
            throw new AccountNotFoundException();
        }
    }



    private TenmoAccount mapRowToAccount(SqlRowSet rs) {
        TenmoAccount acc = new TenmoAccount();
        acc.setAccountId(rs.getInt("account_id"));
        acc.setUserId(rs.getInt("user_id"));
        acc.setBalance(rs.getBigDecimal("balance"));
        return acc;
    }

}
