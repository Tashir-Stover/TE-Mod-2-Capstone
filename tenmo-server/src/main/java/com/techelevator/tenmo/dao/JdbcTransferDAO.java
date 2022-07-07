package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.*;
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

    @Override
    public void addToBalance(BigDecimal transferAmount, int accountTo)
    {
        String sql =
                "UPDATE tenmo_account " +
                        "SET balance = balance + ? " +
                        "WHERE account_id = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferAmount, accountTo);

    }

    @Override
    public void subtractFromBalance(BigDecimal transferAmount, int accountFrom)
    {
        String sql =
                "UPDATE tenmo_account " +
                        "SET balance = balance - ? " +
                        "WHERE account_id = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferAmount, accountFrom);

    }

    @Override
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

    @Override
    public TenmoTransfer getTransferById(int transferId){
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "FROM transfer " +
                "WHERE transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);

        if (results.next()) {
            return mapRowToTenmoTransfer(results);
        } else {
            throw new TransferNotFoundException();
        }
    }

    @Override
    public int getTransferId(){
        String sql = 
    }

    @Override
    public int getTransferTypeById(int transferId){
        String sql = "SELECT transfer_type_id " +
                "FROM transfer " +
                "WHERE transfer_id = ?;";
        int transferTypeId = jdbcTemplate.queryForObject(sql, Integer.class, transferId);
        return transferTypeId;
    }

    @Override
    public int getTransferStatusById(int transferId){
        String sql = "SELECT transfer_status_id " +
                "FROM transfer " +
                "WHERE transfer_id = ?;";
        int transferStatusId = jdbcTemplate.queryForObject(sql, Integer.class, transferId);
        return transferStatusId;
    }

    @Override
    public String getTransferTypeDescById(int transferTypeId){
        String sql = "SELECT transfer_type_desc " +
                "FROM transfer_type " +
                "WHERE transfer_type_id = ?;";
        String transferTypeDesc = jdbcTemplate.queryForObject(sql, String.class, transferTypeId);
        return transferTypeDesc;
    }

    @Override
    public String getTransferStatusDescById(int transferStatusId){
        String sql = "SELECT transfer_status_desc " +
                "FROM transfer_status " +
                "WHERE transfer_status_id = ?;";
        String transferStatusDesc = jdbcTemplate.queryForObject(sql, String.class, transferStatusId);
        return transferStatusDesc;
    }

    @Override
    public int getSendingAccountId(int transferId){
        return getTransferById(transferId).getAccountFrom();
    }

    @Override
    public int getReceivingAccountId(int transferId){
        return getTransferById(transferId).getAccountTo();
    }

    private TenmoTransfer mapRowToTenmoTransfer(SqlRowSet rs) {
        TenmoTransfer tt = new TenmoTransfer();
        tt.setTransferId(rs.getInt("transfer_id"));
        tt.setTransferTypeId(rs.getInt("transfer_type_id"));
        tt.setTransferStatusId(rs.getInt("transfer_status_id"));
        tt.setAccountFrom(rs.getInt("account_from"));
        tt.setAccountTo(rs.getInt("account_to"));
        tt.setAmount(rs.getBigDecimal("amount"));
        return tt;
    }
}
