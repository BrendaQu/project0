package com.company;

import javax.swing.plaf.nimbus.State;
import javax.xml.transform.Result;
import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class UserDaoImpl implements UserDao {
    Connection connection;
    public UserDaoImpl(){
        this.connection = ConnectionFactory.getConnection();
    }

    @Override
    public Employee getEmployeeByLogin(String email, String password) throws SQLException {
        Employee employee = new Employee();
        String sql = "select * from employees where email = '" + email + "' and password = '" + password +"'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        if(resultSet != null) {
            int id = resultSet.getInt(1);
            String emp_fname = resultSet.getString(2);
            String emp_lname = resultSet.getString(3);
            String emp_email = resultSet.getString(4);
            String emp_pass = resultSet.getString(5);
            employee = new Employee(id, emp_fname, emp_lname, emp_email, emp_pass);
        } else {
            System.out.println("No record found \n");
        }

        return employee;
    }

    @Override
    public Customer getCustomerByLogin(String email, String password) throws SQLException {
        Customer customer = new Customer();
        String sql = "select * from customers where email = '" + email + "' and password = '" + password +"'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        if(resultSet != null) {
            int id = resultSet.getInt(1);
            String cus_fname = resultSet.getString(2);
            String cus_lname = resultSet.getString(3);
            String cus_email = resultSet.getString(4);
            String cus_pass = resultSet.getString(5);
            customer = new Customer(id, cus_fname, cus_lname, cus_email, cus_pass);
        } else {
            System.out.println("No record found \n");
        }

        return customer;
    }

    @Override
    public void registerCustomer(String firstName, String lastName, String email, String password, float balance) throws SQLException {
        String sql = "CALL insert_newpendingcustomer(?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);
        preparedStatement.setString(3, email);
        preparedStatement.setString(4, password);
        preparedStatement.setFloat(5, balance);
        int count = preparedStatement.executeUpdate();
        if (count > 0)
            System.out.println("New customer account created. Please wait for email for account approval. \n");
        else
            System.out.println("Account was not created. Something when wrong. \n");
    }

    @Override
    public List<Customer> getPendingCustomers() throws SQLException {
        List<Customer> pending_customers = new ArrayList<>();
        String sql = "Select * from pending_new_customers";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()) {
            int pending_id = resultSet.getInt(1);
            String fname = resultSet.getString(2);
            String lname = resultSet.getString(3);
            String email = resultSet.getString(4);
            String pass = resultSet.getString(5);
            float start_bal = resultSet.getFloat(6);
            Customer customer = new Customer(pending_id,fname,lname,email,pass,start_bal);
            pending_customers.add(customer);
        }

        return pending_customers;
    }

    @Override
    public Customer addPendingById(int id) throws SQLException {
        Customer customer = new Customer();
        String sql = "select * from pending_new_customers where PendingCustID = " + id;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        if(resultSet != null){
            int pend_id = resultSet.getInt(1);
            String fname = resultSet.getString(2);
            String lname = resultSet.getString(3);
            String email = resultSet.getString(4);
            String pass = resultSet.getString(5);
            float bal = resultSet.getFloat(6);
            customer = new Customer(pend_id,fname, lname, email, pass, bal);
        }
        else {
            System.out.println("Something went wrong \n");
        }

        return customer;

    }

    @Override
    public void addCustomer(String firstName, String lastName, String email, String password) throws SQLException {
        String sql = "CALL insert_newcustomer(?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);
        preparedStatement.setString(3, email);
        preparedStatement.setString(4, password);
        int count = preparedStatement.executeUpdate();
        if (count > 0)
            System.out.println("New customer added \n");
        else
            System.out.println("Something went wrong \n");
    }

    @Override
    public Customer getCustByEmail(String email) throws SQLException {
        Customer customer = new Customer();
        String sql = "select * from customers where Email = '" + email +"'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        if(resultSet != null) {
            int id = resultSet.getInt(1);
            String fname = resultSet.getString(2);
            String lname = resultSet.getString(3);
            String cus_email = resultSet.getString(4);
            String pass = resultSet.getString(5);
            customer = new Customer(id, fname, lname, cus_email, pass);
        }
        return customer;

    }

    @Override
    public void addAccount(float Balance, int customerID) throws SQLException {
       String sql = "insert into accounts (CustomerID, Balance) values(?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, customerID);
        preparedStatement.setFloat(2, Balance);
        int count = preparedStatement.executeUpdate();
        if(count > 0)
            System.out.println("Account added \n");
        else
            System.out.println("Something went wrong \n" );
    }

    @Override
    public void deletePendingCustByID(int id) throws SQLException {
        String sql = "delete from pending_new_customers where PendingCustID= ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,id);
        int count = preparedStatement.executeUpdate();
        if(count > 0)
            System.out.println("Pending status deleted \n");
        else
            System.out.println("Something went wrong \n");
    }

    @Override
    public void addPendingAccount(int customerID, float balance) throws SQLException {
        String sql = "CALL insert_newpendingaccount(?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, customerID);
        preparedStatement.setFloat(2, balance);
        int count = preparedStatement.executeUpdate();
        if (count > 0)
            System.out.println("New bank account is created. An email will be send to you when it's approved \n");
        else
            System.out.println("An account was not created. Something when wrong \n");
    }

    @Override
    public List<Account> getPendingAccounts() throws SQLException {
        List<Account> accounts = new ArrayList<>();
        String sql = "select * from pending_accounts";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()){
            int pend_acc_id = resultSet.getInt(1);
            int cus_id = resultSet.getInt(2);
            float s_bal = resultSet.getFloat(3);
            Account account = new Account(pend_acc_id, cus_id, s_bal);
            accounts.add(account);
        }
        return accounts;
    }

    @Override
    public Account addPendingAccByID(int id) throws SQLException {
        Account account = new Account();
        String sql = "select * from pending_accounts where PendingAccID = " + id;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        if(resultSet != null){
            int pend_id = resultSet.getInt(1);
            int cus_id = resultSet.getInt(2);
            float s_bal = resultSet.getFloat(3);
            account = new Account(pend_id, cus_id, s_bal);
        }
        else {
            System.out.println("Something went wrong \n");
        }

        return account;
    }

    @Override
    public void deletePendingAccByID(int id) throws SQLException {
        String sql = "delete from pending_accounts where PendingAccID= ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,id);
        int count = preparedStatement.executeUpdate();
        if(count > 0)
            System.out.println("Pending acc status deleted \n");
        else
            System.out.println("Something went wrong \n");
    }


    @Override
    public void logTransactions(int customerID, int type, float amount) throws SQLException {
        String sql = "CALL insert_transaction(?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, customerID);
        preparedStatement.setInt(2, type);
        preparedStatement.setFloat(3, amount);
        int count = preparedStatement.executeUpdate();
        if (count > 0)
            System.out.println("Transaction logged.\n");
        else
            System.out.println("Transaction not logged. Something when wrong \n");
    }

    @Override
    public List<Transaction> getTransactionList() throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "select * from transactions";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()){
            int trans_id = resultSet.getInt(1);
            int cusID = resultSet.getInt(2);
            String type = resultSet.getString(3);
            float amount = resultSet.getFloat(4);
            Transaction transaction= new Transaction(trans_id, cusID,type,amount);
            transactions.add(transaction);
        }
        return transactions;
    }

    @Override
    public List<Account> getAccountsByCusId(int id) throws SQLException {
        List<Account> accounts = new ArrayList<>();
        String sql = "select * from accounts where CustomerID = " + id;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()){
            int acc_id = resultSet.getInt(1);
            int cus_id = resultSet.getInt(2);
            float bal = resultSet.getFloat(3);
            float pendTrans = resultSet.getFloat(4);
            Account account = new Account(acc_id, cus_id, bal, pendTrans);
            accounts.add(account);
        }
        return accounts;
    }

    @Override
    public Account getAccountByAccID(int cus_id, int acc_id) throws SQLException {
        Account account = new Account();
        String sql = "select * from accounts where CustomerID = '" + cus_id +"'"+ " and AccountID = '" + acc_id +"'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        if(resultSet != null) {
            int accId = resultSet.getInt(1);
            int cusId = resultSet.getInt(2);
            float bal = resultSet.getFloat(3);
            float pend_trans = resultSet.getFloat(4);
            account = new Account(accId, cusId, bal, pend_trans);
        }
        return account;
    }

    @Override
    public void AccountWithdraw(int acc_id, int cus_id, float amount) throws SQLException {
        Account account = new Account();
        String sql = "select * from accounts where CustomerID = '" + cus_id +"'"+ " and AccountID = '" + acc_id +"'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        if(resultSet != null) {
            int accId = resultSet.getInt(1);
            int cusId = resultSet.getInt(2);
            float bal = resultSet.getFloat(3);
            float pend_trans = resultSet.getFloat(4);
            account = new Account(accId, cusId, bal, pend_trans);
        }
        if(amount <= account.getAccBal() && amount > 0){
            float update_balance = account.getAccBal() - amount;
            String n_sql = "Update accounts set Balance = "+ update_balance + "where AccountID = " +acc_id;
            PreparedStatement preparedStatement = connection.prepareStatement(n_sql);
            int count = preparedStatement.executeUpdate();
            if(count > 0)
                System.out.println("Amount Withdraw Successfully \n");
            else
                System.out.println("Amount Withdraw Unsuccessful. Something went wrong.\n");
        } else
            System.out.println("Invalid amount. Withdraw denied. \n");
    }

    @Override
    public void AccountDeposit(int acc_id, int cus_id, float amount) throws SQLException {
        Account account = new Account();
        String sql = "select * from accounts where CustomerID = '" + cus_id +"'"+ " and AccountID = '" + acc_id +"'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        if(resultSet != null) {
            int accId = resultSet.getInt(1);
            int cusId = resultSet.getInt(2);
            float bal = resultSet.getFloat(3);
            float pend_trans = resultSet.getFloat(4);
            account = new Account(accId, cusId, bal, pend_trans);
        }
        if(amount > 0){
            float update_balance = account.getAccBal() + amount;
            String n_sql = "Update accounts set Balance = "+ update_balance + "where AccountID = " +acc_id;
            PreparedStatement preparedStatement = connection.prepareStatement(n_sql);
            int count = preparedStatement.executeUpdate();
            if(count > 0)
                System.out.println("Amount Deposit Successfully \n");
            else
                System.out.println("Amount Deposit Unsuccessful. Something went wrong.\n");
        } else
            System.out.println("Invalid amount. Deposit denied. \n");
    }

    @Override
    public void PostTransfer(int cus_id, int atf_id, int att_id, float amount) throws SQLException {
        Account account = new Account();
        float amount_transfer = amount;
        float amount_taken = -1 * amount;
        String sql = "select * from accounts where CustomerID = '" + cus_id +"'"+ " and AccountID = '" + atf_id +"'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        if(resultSet != null) {
            int accId = resultSet.getInt(1);
            int cusId = resultSet.getInt(2);
            float bal = resultSet.getFloat(3);
            float pend_trans = resultSet.getFloat(4);
            account = new Account(accId, cusId, bal, pend_trans);
        }
        if(amount < account.getAccBal() && amount > 0){
            String n_sql1 = "Update accounts set PendingTransfer = "+ amount_taken + " where AccountID = " +atf_id;
            String n_sql2 = "Update accounts set PendingTransfer = " + amount_transfer + " where AccountID = " +att_id;
            String n_sql3 = "Update accounts set AccountTransferID = " + att_id + " where AccountID = " + atf_id;
            String n_sql4 = "Update accounts set AccountTransferID = " + atf_id + " where AccountID = " + att_id;
            PreparedStatement preparedStatement1 = connection.prepareStatement(n_sql1);
            PreparedStatement preparedStatement2 = connection.prepareStatement(n_sql2);
            PreparedStatement preparedStatement3 = connection.prepareStatement(n_sql3);
            PreparedStatement preparedStatement4 = connection.prepareStatement(n_sql4);
            int count1 = preparedStatement1.executeUpdate();
            int count2 = preparedStatement2.executeUpdate();
            int count3 = preparedStatement3.executeUpdate();
            int count4 = preparedStatement4.executeUpdate();
            if(count1 > 0 && count2 > 0 && count3 > 0 && count4 > 0) {
                System.out.println("Transfer amount posted successfully \n");
                System.out.println("From Account ID: " + atf_id + " To Account ID: " + att_id + " Pending Transfer: " + amount_transfer + "\n");
            }
            else
                System.out.println("Transfer amount Unsuccessful. Something went wrong. \n");
        } else
            System.out.println("Invalid amount. Transfer denied.\n");
    }

    @Override
    public List<Account> getPendingTransfers(int cus_id) throws SQLException {
        List<Account> accounts = new ArrayList<>();
        String sql = "select * from accounts where CustomerID = " + cus_id + " and PendingTransfer != 0 and PendingTransfer > 0";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()){
            int acc_id = resultSet.getInt(1);
            int cusID = resultSet.getInt(2);
            float bal = resultSet.getFloat(3);
            float pendTrans = resultSet.getFloat(4);
            int acc_t_id = resultSet.getInt(5);
            Account account = new Account(acc_id, cusID, bal, pendTrans,acc_t_id);
            accounts.add(account);
        }
        return accounts;
    }

    @Override
    public float getPendingTransfersAmount(int acc_id) throws SQLException {
        String sql = "select * from accounts where AccountID = '" + acc_id +"'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        float ptrans = 0f;
        while(resultSet.next()){
            ptrans = resultSet.getFloat(4);
        }
        return ptrans;
    }


    @Override
    public void AcceptTransfer(int cus_id, int acc_id) throws SQLException {
        Account account = new Account();
        String sql = "select * from accounts where CustomerID = '" + cus_id +"'"+ " and AccountID = '" + acc_id +"'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        if(resultSet !=  null){
            int accID = resultSet.getInt(1);
            int cusID = resultSet.getInt(2);
            float bal = resultSet.getFloat(3);
            float pend_trans = resultSet.getFloat(4);
            int accTID = resultSet.getInt(5);
            account = new Account(accID,cusID,bal,pend_trans,accTID);
            float new_bal_acc = account.getAccBal() + account.getPendTrans();


            String sql1 = "select * from accounts where AccountID = '" +account.getAccTransID()+"'";
            Account account1 = new Account();
            Statement statement1 = connection.createStatement();
            ResultSet resultSet1 = statement1.executeQuery(sql1);
            resultSet1.next();
            if(resultSet1 != null){
                int accID1 = resultSet1.getInt(1);
                int cusID1 = resultSet1.getInt(2);
                float bal1 = resultSet1.getFloat(3);
                float pend_trans1 = resultSet1.getFloat(4);
                int accTID1 = resultSet1.getInt(5);
                account1 = new Account(accID1,cusID1,bal1,pend_trans1,accTID1);
                float new_bal_acc1 = account1.getAccBal() + account1.getPendTrans();
                String n_sql = "Update accounts set Balance = '" + new_bal_acc + "' where AccountID = '" +accID +"'";
                String n_sql1 = "Update accounts set Balance = '" + new_bal_acc1 + "' where AccountID = '" +accID1 +"'";
                String n_sql2 = "Update accounts set AccountTransferID = NULL where AccountID = '"+accID+"'";
                String n_sql3 = "Update accounts set AccountTransferID = NULL where AccountID = '" +accID1+ "'";
                String n_sql4 = "Update accounts set PendingTransfer = 0 where AccountID = '" +accID+"'";
                String n_sql5 = "Update accounts set PendingTransfer = 0 where AccountID = '" +accID1+"'";
                PreparedStatement preparedStatement1 = connection.prepareStatement(n_sql);
                PreparedStatement preparedStatement2 = connection.prepareStatement(n_sql1);
                PreparedStatement preparedStatement3 = connection.prepareStatement(n_sql2);
                PreparedStatement preparedStatement4 = connection.prepareStatement(n_sql3);
                PreparedStatement preparedStatement5 = connection.prepareStatement(n_sql4);
                PreparedStatement preparedStatement6 = connection.prepareStatement(n_sql5);
                int count1 = preparedStatement1.executeUpdate();
                int count2 = preparedStatement2.executeUpdate();
                int count3 = preparedStatement3.executeUpdate();
                int count4 = preparedStatement4.executeUpdate();
                int count5 = preparedStatement5.executeUpdate();
                int count6 = preparedStatement6.executeUpdate();
                if(count1 > 0 && count2 > 0 && count3 > 0 && count4 > 0 && count5 > 0 && count6 >0){
                    System.out.println("Accept Transfer Successful \n");
                }
                else
                    System.out.println("Transfer was not successful. Something went wrong. \n");

            }

        } else
            System.out.println("Did not accept transfer. Something went wrong. \n");

    }

}
