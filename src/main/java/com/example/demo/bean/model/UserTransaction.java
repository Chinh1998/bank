/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package com.example.demo.bean.model;

/**
 * [OVERVIEW] XXXXX.
 *
 * @author: (VNEXT) ChinhTQ
 * @version: 1.0
 * @History
 * [NUMBER]  [VER]     [DATE]          [USER]             [CONTENT]
 * --------------------------------------------------------------------------
 * 001       1.0       2020/04/14      (VNEXT) ChinhTQ       Create new
*/
public class UserTransaction {

    private String bankName;
    private String typeTrading;
    private String dateTrading;
    private double money;
    private double fee;

 
    public UserTransaction(String bankName, String typeTrading, String dateTrading, double money, double fee) {
        super();
        this.bankName = bankName;
        this.typeTrading = typeTrading;
        this.dateTrading = dateTrading;
        this.money = money;
        this.fee = fee;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getTypeTrading() {
        return typeTrading;
    }

    public void setTypeTrading(String typeTrading) {
        this.typeTrading = typeTrading;
    }

    public String getDateTrading() {
        return dateTrading;
    }

    public void setDateTrading(String dateTrading) {
        this.dateTrading = dateTrading;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

}
