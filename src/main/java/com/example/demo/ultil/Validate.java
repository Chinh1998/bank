/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package com.example.demo.ultil;

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

public class Validate {

    public static final String USERNAME = "[^0-9][A-Za-z0-9]{4,10}";
    public static final String DATE = "(\\d{2}|\\d{4})[\\/\\.](0?[1-9]|[12]\\d|3[01])[\\/\\.](0?[1-9]|[12]\\d|3[01])\\b";
    public static final String EMAIL = "[A-Za-z1-9]+@[g][m][a][i][l].[c][o][m]$";
    public static final String AMOUNT = "[0-9]+";
    public static final String BANK_ID = "[123]";
    public static final String NUMBER_PHONE = "[0]([0-9]+){9,10}$";
    public static final String PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,8}$";

    public Validate() {

    }
}
