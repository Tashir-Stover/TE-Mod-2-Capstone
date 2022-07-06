package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.AccountNotFoundException;
import com.techelevator.tenmo.model.TenmoAccount;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

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



    private TenmoAccount mapRowToAccount(SqlRowSet rs) {
        TenmoAccount acc = new TenmoAccount();
        acc.setAccountId(rs.getInt("user_id"));
        acc.setBalance(rs.getBigDecimal("balance"));
        return acc;
    }

}
