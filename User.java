package com.company;

public class User {
    protected int id;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String password;

    public User(int id, String firstName, String lastName, String email, String password){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public User(){

    }

    public int getId() {
        return id;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getEmail(){
        return email;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setPassword(String password){
        this.password = password;
    }

}

class Employee extends User{

    public Employee(int id, String firstName, String lastName, String email, String password) {
        super(id, firstName, lastName, email, password);
    }
    public Employee(){}
}

class Customer extends User{
    private int acc_id;
    private int pcus_id;
    private int cus_id;
    private float balance;
    private float start_bal;
    private float pending_trans;

    public Customer(int id, String firstName, String lastName, String email, String password) {
        super(id, firstName, lastName, email, password);
    }
    public Customer(){}

    public Customer (int pcus_id, String firstName, String lastName, String email, String password, float start_bal){
        this.pcus_id = pcus_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.start_bal = start_bal;

    }
    public void Account(int acc_id, int cus_id, float balance, float pending_trans){
        this.acc_id = acc_id;
        this.cus_id = cus_id;
        this.balance = balance;
        this.pending_trans = pending_trans;
    }

    public float getStartBal(){
        return start_bal;
    }
    public int getPcus_id(){
        return pcus_id;
    }
    public String getPassword(){
        return password;
    }

}
