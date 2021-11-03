package com.company;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    //Log in, check that email and passwords match on tables
    Employee getEmployeeByLogin(String email, String password) throws SQLException;
    Customer getCustomerByLogin(String email, String password) throws SQLException;
    //Register new customers in Pending New Customers table
    void registerCustomer(String firstName, String lastName,String email, String password, float Balance) throws SQLException;

    //Employee Tasks
    //view pending new customer list
    List<Customer> getPendingCustomers() throws SQLException;
    //approve account by pending id for customer
    Customer addPendingById(int id) throws SQLException;
    //Add customer to customer table in database
    void addCustomer(String firstName, String lastName, String email, String password) throws SQLException;
    //Get customerID by email
    Customer getCustByEmail(String email) throws SQLException;
    //Add account to account table in database, after approval of new customers and existing customers accounts
    //For new users add to customer table first to create customer ID
    void addAccount(float Balance,int customerID) throws SQLException;
    //Delete pending account after adding account for customers
    void deletePendingCustByID(int id) throws SQLException;

    //Add new account from existing customers in Pending Accounts table
    void addPendingAccount(int customerID, float balance) throws SQLException;
    List<Account> getPendingAccounts() throws SQLException;

    //Approve account by pending id for account
    Account addPendingAccByID(int id) throws SQLException;

    //Delete pending account for accounts
    void deletePendingAccByID(int id) throws SQLException;


    //View a customer's account by Customer ID
    List<Account> getAccountsByCusId(int id) throws SQLException;

    //View a customer's account by Customer ID and Account ID
    Account getAccountByAccID(int cus_id, int acc_id) throws SQLException;

    //Withdraw Amount from account
    void AccountWithdraw(int acc_id, int cus_id, float amount) throws SQLException;
    //Deposit Amount from account
    void AccountDeposit(int acc_id, int cus_id, float amount) throws SQLException;

    // Post Transfer Amount
    void PostTransfer(int cus_id, int atf_id, int att_id, float amount) throws SQLException;

    // View Pending Transfers
    List<Account> getPendingTransfers(int cus_id) throws SQLException;

    //Get Pending Transfers by Account ID
    float getPendingTransfersAmount(int acc_id) throws SQLException;
    //Accept Transfer Amount
    void AcceptTransfer(int cus_id, int acc_id) throws SQLException;

    //log transactions
    void logTransactions(int customerID, int type, float amount) throws SQLException;

    //List of transactions
    List<Transaction> getTransactionList() throws SQLException;
}
