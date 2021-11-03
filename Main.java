package com.company;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        // User type menu
        UserDao dao = UserDaoFactory.getUserDao();
        Scanner scanner = new Scanner(System.in);
        boolean login_menu = true;
        while (login_menu) {
            //Login Menu
            System.out.println("Welcome to Bank Zero Inc.");
            System.out.println("Select user type: ");
            System.out.println("Press 1: Employee login");
            System.out.println("Press 2: Customer login");
            System.out.println("Press 3: Register for new customer account");
            System.out.println("Press 4: Exit System \n");

            int userType = scanner.nextInt();
            scanner.nextLine();
            switch (userType) {
                case 1:
                    boolean emp_portal = true;
                    // Prompt Employee to Log in
                    System.out.println("Please enter your employee login information below");
                    System.out.println("Email: ");
                    String emp_email = scanner.nextLine();
                    System.out.println("Password: ");
                    String emp_password = scanner.nextLine();
                    System.out.println("Welcome " + dao.getEmployeeByLogin(emp_email, emp_password).getFirstName() + " " + dao.getEmployeeByLogin(emp_email, emp_password).getLastName());
                    System.out.println("Employee ID: " + dao.getEmployeeByLogin(emp_email, emp_password).getId());
                    System.out.println();
                    while(emp_portal) {
                        //Employee Menu
                        System.out.println("Employee Menu: ");
                        System.out.println("Press 1: New Customer Approvals");
                        System.out.println("Press 2: New Account Approvals");
                        System.out.println("Press 3: View A Customer's Bank Accounts By ID");
                        System.out.println("Press 4: View All Transaction Logs");
                        System.out.println("Press 5: Log out \n");
                        int empMenuOption = scanner.nextInt();
                        scanner.nextLine();
                        switch (empMenuOption) {
                            case 1:
                                //Get list of customers from Pending New Customers table
                                List<Customer> customerList = dao.getPendingCustomers();
                                for (Customer item : customerList) {
                                    System.out.println("Pending ID: " + item.getPcus_id() + ", Name: " + item.getFirstName() + " " + item.getLastName() + ", Email: " + item.getEmail() + ", Start Balance: " + item.getStartBal());
                                }
                                //Return list if it's not empty
                                if (!customerList.isEmpty()) {
                                    System.out.println("Enter in Pending ID to approve account");
                                    int pend_id = scanner.nextInt();
                                    scanner.nextLine();
                                    Customer pend_cus = dao.addPendingById(pend_id);
                                    dao.addCustomer(pend_cus.getFirstName(), pend_cus.getLastName(), pend_cus.getEmail(), pend_cus.getPassword());
                                    int cus_id = dao.getCustByEmail(pend_cus.getEmail()).getId();
                                    dao.addAccount(pend_cus.getStartBal(), cus_id);
                                    dao.logTransactions(cus_id, 1, pend_cus.getStartBal());
                                    dao.deletePendingCustByID(pend_id);
                                } else
                                    System.out.println("No new pending customers approvals \n");
                                break;
                            case 2:
                                //Get list of pending accounts from Pending Accounts table
                                List<Account> PendingAccounts = dao.getPendingAccounts();
                                for (Account item : PendingAccounts) {
                                    System.out.println("Pending Account ID: " + item.getPendAccID() + ", Customer ID: " + item.getCusID() + ", Start Balance: " + item.getPendAccSBal());
                                }
                                //Return list if not empty
                                if(!PendingAccounts.isEmpty()) {
                                    System.out.println("Enter in Pending Account ID to approve or reject");
                                    int pend_acc_id = scanner.nextInt();
                                    scanner.nextLine();
                                    System.out.println("Enter in 1 to Approve or 2 to Reject");
                                    int a_r_acc = scanner.nextInt();
                                    scanner.nextLine();
                                    //Approve or Reject accounts from existing customers
                                    if(a_r_acc == 1) {
                                        Account pend_acc = dao.addPendingAccByID(pend_acc_id);
                                        dao.addAccount(pend_acc.getPendAccSBal(), pend_acc.getCusID());
                                        dao.logTransactions(pend_acc.getCusID(), 2, pend_acc.getPendAccSBal());
                                        dao.deletePendingAccByID(pend_acc_id);
                                        System.out.println("Pending Account ID: " + pend_acc_id + " approved and added to accounts table \n");
                                    }
                                    else if (a_r_acc == 2){
                                        dao.deletePendingAccByID(pend_acc_id);
                                        System.out.println("Pending Account ID: " + pend_acc_id + " rejected and deleted from pending list \n");
                                    }
                                    else {
                                        System.out.println("Incorrect option, account was not approved or rejected \n");
                                    }
                                } else
                                    System.out.println("No Pending Account Approvals \n");
                                break;
                            case 3:
                                //Enter in Customer's ID to view all account information
                                System.out.println("Enter in Customers' ID to view Accounts");
                                int cusID = scanner.nextInt();
                                scanner.nextLine();
                                List<Account> Accounts = dao.getAccountsByCusId(cusID);
                                for (Account item : Accounts) {
                                    System.out.println("Account ID: " + item.getAccID() + ", Customer ID: " + item.getCusID() + ", Balance: " + item.getAccBal() + ", Pending Transfers: " + item.getPendTrans());
                                }
                                System.out.println();
                                break;
                            case 4:
                                //Get a list of all Transactions in the system
                                List<Transaction> Transactions = dao.getTransactionList();
                                for (Transaction item : Transactions) {
                                    System.out.println("Transaction ID: " + item.getTransID() + ", Customer ID: " + item.getCusID() + ", Type: " + item.getType() + ", Amount: " + item.getAmount());
                                }
                                System.out.println();
                                break;
                            case 5:
                                //Log out the employee and return to main system menu
                                emp_portal = false;
                                break;
                            default:
                                //Enter in wrong key
                                System.out.println("Please Select 1-5 \n");
                                break;

                        }
                    }
                    break;
                case 2:
                    boolean cus_portal = true;
                    //Prompt Customers to login
                    System.out.println("Please enter your customer login information below");
                    System.out.println("Email: ");
                    String cus_email = scanner.nextLine();
                    System.out.println("Password: ");
                    String cus_password = scanner.nextLine();
                    Customer customer = dao.getCustomerByLogin(cus_email, cus_password);
                    System.out.println("Welcome " + customer.getFirstName() + " " + customer.getLastName());
                    System.out.println("Customer ID: " + customer.getId());
                    while(cus_portal) {
                        // Customer Menu
                        System.out.println("Customer Menu: ");
                        System.out.println("Press 1: View Accounts");
                        System.out.println("Press 2: Withdraw Amount");
                        System.out.println("Press 3: Deposit Amount");
                        System.out.println("Press 4: Transfer Money");
                        System.out.println("Press 5: Accept Money Transfer");
                        System.out.println("Press 6: Apply for new bank account");
                        System.out.println("Press 7: Log Out \n");
                        int cusMenuOption = scanner.nextInt();
                        scanner.nextLine();
                        switch (cusMenuOption) {
                            case 1:
                                //Get list of all accounts the customer has
                                List<Account> Accounts = dao.getAccountsByCusId(customer.getId());
                                for (Account item : Accounts) {
                                    System.out.println("Account ID: " + item.getAccID());
                                }
                                //View accounts and balances
                                System.out.println("Enter Account ID to view balance and any pending transfers: ");
                                int accID = scanner.nextInt();
                                Account account = dao.getAccountByAccID(customer.getId(), accID);
                                System.out.println("Account ID: " + account.getAccID() + ", Balance: " + account.getAccBal() + ", Pending Transfer: " + account.getPendTrans() +"\n");
                                break;
                            case 2:
                                //Withdraw from a specific account
                                System.out.println("Enter Account ID that you wish to withdraw from: ");
                                int aw_ID = scanner.nextInt();
                                scanner.nextLine();
                                System.out.println("Enter withdraw amount: ");
                                float w_amount = scanner.nextFloat();
                                scanner.nextLine();
                                //Method will check if account has enough funds and update tables
                                dao.AccountWithdraw(aw_ID, customer.getId(), w_amount);
                                //It will log transaction to system if the withdrawal was successful
                                if(dao.getAccountByAccID(customer.getId(),aw_ID).getAccBal() > w_amount){
                                    dao.logTransactions(customer.getId(), 3, w_amount);
                                }
                                System.out.println();
                                break;
                            case 3:
                                //Deposit into specific account
                                System.out.println("Enter Account ID that you wish to deposit to: ");
                                int ad_ID = scanner.nextInt();
                                scanner.nextLine();
                                System.out.println("Enter deposit amount: ");
                                float d_amount = scanner.nextFloat();
                                scanner.nextLine();
                                //Method will check for negative values and update tables
                                dao.AccountDeposit(ad_ID, customer.getId(), d_amount);
                                //Will only log transactions if deposit is not negative
                                if(d_amount >= 0) {
                                    dao.logTransactions(customer.getId(), 4, d_amount);
                                }
                                System.out.println();
                                break;
                            case 4:
                                //Transfer money to customer or other customers accounts
                                System.out.println("Enter Account ID you want to transfer amount from: ");
                                int atf_id = scanner.nextInt();
                                scanner.nextLine();
                                System.out.println("Enter Account ID you want to transfer amount to: ");
                                int att_id = scanner.nextInt();
                                scanner.nextLine();
                                System.out.println("Enter amount you want to transfer: ");
                                float amount_trans = scanner.nextFloat();
                                //Will Post transaction but will not deduct funds until the user accepts
                                dao.PostTransfer(customer.getId(), atf_id, att_id, amount_trans);
                                dao.logTransactions(customer.getId(), 5, amount_trans);
                                System.out.println();
                                break;
                            case 5:
                                //List out all pending transfers
                                List<Account> PendTransAccounts = dao.getPendingTransfers(customer.getId());
                                for (Account item : PendTransAccounts) {
                                    System.out.println("Account ID: " + item.getAccID() + ", Pending Transfer: " + item.getPendTrans());
                                }
                                //Accept tranfers by entering Account I, method will update balances on accounts
                                System.out.println("Enter Account ID to accept money transfer: ");
                                int accept_id = scanner.nextInt();
                                scanner.nextLine();
                                dao.logTransactions(customer.getId(), 6, dao.getPendingTransfersAmount(accept_id));
                                dao.AcceptTransfer(customer.getId(), accept_id);
                                Account account2 = dao.getAccountByAccID(customer.getId(), accept_id);
                                System.out.println("Account ID: " + account2.getAccID() + ", Balance: " + account2.getAccBal()+"\n");
                                break;

                            case 6:
                                //Enter in starting amount for account and wait for employee to approve
                                System.out.println("Enter in the amount for new bank account: ");
                                float amount = scanner.nextFloat();
                                scanner.nextLine();
                                dao.addPendingAccount(customer.getId(), amount);
                                System.out.println();
                                break;
                            case 7:
                                //log out of customer menu and got back to main system menu
                                System.out.println("Logging Out...\n");
                                cus_portal = false;
                            default:
                                System.out.println("Please select 1-7 \n");
                        }
                    }
                    break;
                case 3:
                    //Enter in information and wait to approval from employee
                    System.out.println("Register for new account below");
                    System.out.println("First Name: ");
                    String pc_fname = scanner.nextLine();
                    System.out.println("Last Name: ");
                    String pc_lname = scanner.nextLine();
                    System.out.println("Email: ");
                    String pc_email = scanner.nextLine();
                    System.out.println("Password: ");
                    String pc_pass = scanner.nextLine();
                    System.out.println("Starting Balance: ");
                    float pc_bal = scanner.nextFloat();
                    dao.registerCustomer(pc_fname, pc_lname, pc_email, pc_pass, pc_bal);
                    System.out.println();
                    break;
                case 4:
                    //Exitinng out of banking system
                    System.out.println("Exiting.....");
                    System.out.println("Good Bye.\n");
                    login_menu = false;
                    break;
                default:
                    System.out.println("Please select 1-4 \n");
                    break;
            }

        }
    }
}
