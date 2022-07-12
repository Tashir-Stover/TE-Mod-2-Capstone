package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDAO implements TransferDAO {

    private final JdbcTemplate jdbcTemplate;
    public JdbcTransferDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void updateReceiverBalance(TransferDTO transferDTO)
    {
        String sql =
                "UPDATE tenmo_account " +
                        "SET balance = ? " +
                        "WHERE account_id = ?";

        jdbcTemplate.update(sql, transferDTO.getReceiverAcct().getBalance(), transferDTO.getReceiverAcct().getAccountId());

    }

    @Override
    public void updateSenderBalance(TransferDTO transferDTO)
    {
        String sql =
                "UPDATE tenmo_account " +
                        "SET balance = ? " +
                        "WHERE account_id = ?";

        jdbcTemplate.update(sql, transferDTO.getSenderAcct().getBalance(), transferDTO.getSenderAcct().getAccountId());

    }

    @Override
    public void transfer(TransferDTO transferDTO) {

        BigDecimal updatedSenderBalance = transferDTO.getSenderAcct().getBalance().subtract(transferDTO.getAmount());
        BigDecimal updatedReceiverBalance = transferDTO.getReceiverAcct().getBalance().add(transferDTO.getAmount());

        transferDTO.getSenderAcct().setBalance(updatedSenderBalance);
        transferDTO.getReceiverAcct().setBalance(updatedReceiverBalance);

        updateSenderBalance(transferDTO);
        updateReceiverBalance(transferDTO);

        //add this transfer to transfer table
        //clarify what 2, 2 means
        String sql = "INSERT INTO tenmo_transfer(transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES(2, 2, ?, ?, ?);";
        jdbcTemplate.update(sql, transferDTO.getSenderAcct().getAccountId(), transferDTO.getReceiverAcct().getAccountId(), transferDTO.getAmount());
        //add transfer_type table
        sql = "INSERT INTO transfer_type(transfer_type_desc) VALUES ('Send');";
        jdbcTemplate.update(sql);
        //add transfer transfer_status table
        sql = "INSERT INTO transfer_status(transfer_status_desc) VALUES('Approved');";
        jdbcTemplate.update(sql);
    }

    @Override
    public TenmoTransfer getTransferById(int transferId){
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "FROM tenmo_transfer " +
                "WHERE transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);

        if (results.next()) {
            return mapRowToTenmoTransfer(results);
        } else {
            throw new TransferNotFoundException();
        }
    }



    @Override
    public int getTransferTypeById(int transferId){
        String sql = "SELECT transfer_type_id " +
                "FROM tenmo_transfer " +
                "WHERE transfer_id = ?;";
        int transferTypeId = jdbcTemplate.queryForObject(sql, Integer.class, transferId);
        return transferTypeId;
    }

    @Override
    public int getTransferStatusById(int transferId){
        String sql = "SELECT transfer_status_id " +
                "FROM tenmo_transfer " +
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
    public int getSendingAccountId(TransferDTO transferDTO){
        return transferDTO.getSenderAcct().getAccountId();
    }

    @Override
    public int getReceivingAccountId(TransferDTO transferDTO){
        return transferDTO.getReceiverAcct().getAccountId();
    }

    @Override
    public List<TenmoTransfer> getAllTransfersByUserId(int userId){

        List<TenmoTransfer> transfers = new ArrayList<>();
        String sql = "SELECT tenmo_transfer.transfer_id, tenmo_transfer.transfer_type_id, tenmo_transfer.transfer_status_id, tenmo_transfer.account_from, tenmo_transfer.account_to, tenmo_transfer.amount " +
                "FROM tenmo_transfer " +
                "JOIN tenmo_account ON tenmo_account.account_id = tenmo_transfer.account_from " +
                //"JOIN tenmo_user ON tenmo_account.user_id = tenmo_user.user_id " +
                "WHERE tenmo_account.user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);

        while(results.next()){

            transfers.add(mapRowToTenmoTransfer(results));
        }

         sql = "SELECT tenmo_transfer.transfer_id, tenmo_transfer.transfer_type_id, tenmo_transfer.transfer_status_id, tenmo_transfer.account_from, tenmo_transfer.account_to, tenmo_transfer.amount " +
                "FROM tenmo_transfer " +
                "JOIN tenmo_account ON tenmo_account.account_id = tenmo_transfer.account_to " +
               // "JOIN tenmo_user ON tenmo_account.user_id = tenmo_user.user_id " +
                "WHERE tenmo_account.user_id = ?;";
         results = jdbcTemplate.queryForRowSet(sql, userId);

        while(results.next()){

            transfers.add(mapRowToTenmoTransfer(results));
        }

        return transfers;

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
