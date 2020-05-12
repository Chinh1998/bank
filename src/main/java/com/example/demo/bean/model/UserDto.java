/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package com.example.demo.bean.model;

import java.util.Set;

import javax.persistence.ManyToMany;

import com.example.demo.bean.entity.RoleEntity;

/**
 * [OVERVIEW] XXXXX.
 *
 * @author: (VNEXT) ChinhTQ
 * @version: 1.0
 * @History
 * [NUMBER]  [VER]     [DATE]          [USER]             [CONTENT]
 * --------------------------------------------------------------------------
 * 001       1.0       2020/04/27      (VNEXT) ChinhTQ       Create new
*/
public class UserDto {

    private String username;

    private String phoneNumber;

    private String email;

    private String dOBirth;

    private double balance;

    private String bankName;

    
    public UserDto(String username, String phoneNumber, String email, String dOBirth, double balance, String bankName) {
        super();
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.dOBirth = dOBirth;
        this.balance = balance;
        this.bankName = bankName;
    }

    @ManyToMany
    Set<RoleEntity> role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getdOBirth() {
        return dOBirth;
    }

    public void setdOBirth(String dOBirth) {
        this.dOBirth = dOBirth;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    
    
}
