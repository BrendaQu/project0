package com.company;

public class Account {
    int acc_id;
    int cus_id;
    int pend_acc_id;
    int acc_trans_id;
    float acc_bal;
    float acc_pend_trans;
    float acc_pendAcc_sBal;


    public Account(){

    }
    public Account(int acc_id, int cus_id, float acc_bal, float acc_pend_trans){
        this.acc_id = acc_id;
        this.cus_id = cus_id;
        this.acc_bal = acc_bal;
        this.acc_pend_trans = acc_pend_trans;
    }
    public Account(int acc_id, int cus_id, float acc_bal, float acc_pend_trans,int acc_trans_id){
        this.acc_id = acc_id;
        this.cus_id = cus_id;
        this.acc_bal = acc_bal;
        this.acc_pend_trans = acc_pend_trans;
        this.acc_trans_id = acc_trans_id;
    }
    public Account(int acc_id, int cus_id, float acc_bal, float acc_pend_trans, float acc_pendAcc_sBal){
        this.acc_id = acc_id;
        this.cus_id = cus_id;
        this.acc_bal = acc_bal;
        this.acc_pend_trans = acc_pend_trans;
        this.acc_pendAcc_sBal = acc_pendAcc_sBal;
    }
    public Account(int pend_acc_id, int cus_id, float acc_pendAcc_sBal){
        this.pend_acc_id = pend_acc_id;
        this.cus_id = cus_id;
        this.acc_pendAcc_sBal = acc_pendAcc_sBal;
    }

    public int getAccID(){
        return acc_id;
    }
    public int getCusID(){
        return cus_id;
    }
    public int getPendAccID(){
        return pend_acc_id;
    }
    public int getAccTransID() {return acc_trans_id;}
    public float getAccBal(){
        return acc_bal;
    }
    public float getPendTrans(){
        return acc_pend_trans;
    }
    public float getPendAccSBal(){
        return acc_pendAcc_sBal;
    }

}
