package com.company;

//Transaction class for transaction logs
public class Transaction {
    protected int trans_id;
    protected int cus_id;
    protected String type;
    protected float amount;

    public Transaction(int trans_id, int cus_id, String type, float amount){
        this.trans_id = trans_id;
        this.cus_id = cus_id;
        this.type = type;
        this.amount = amount;
    }
    //getter methods
    public int getTransID() {
        return trans_id;
    }
    public int getCusID(){
        return cus_id;
    }
    public String getType(){
        return type;
    }
    public float getAmount(){
        return amount;
    }
}
