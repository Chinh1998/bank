/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package com.example.demo.service;

import java.util.List;

import com.example.demo.bean.ResultBean;
import com.example.demo.bean.entity.TransactionEntity;
import com.example.demo.bean.model.UserTransaction;
import com.example.demo.ultil.ApiValidateException;

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

public interface TransactionService {

    /**
     * sendMoney
     * @author: (VNEXT) ChinhTQ
     * @param json
     * @return TransactionEntity
     * @throws ApiValidateException
     */
    public ResultBean sendMoney(String json) throws ApiValidateException;

    /**
     * withdrawMoney
     * @author: (VNEXT) ChinhTQ
     * @param json
     * @return TransactionEntity
     * @throws ApiValidateException
     */
    public TransactionEntity withdrawMoney(String json) throws ApiValidateException;

    /**
     * transferMoney
     * @author: (VNEXT) ChinhTQ
     * @param json
     * @return TransactionEntity
     * @throws ApiValidateException
     */
    public TransactionEntity transferMoney(String json) throws ApiValidateException;

    /**
     * getAllByUser
     * @author: (VNEXT) ChinhTQ
     * @return list transaction of one user
     * @throws ApiValidateException
     */
    public List<UserTransaction> getAllByUser() throws ApiValidateException;

    /**
     * getTransactionByUserAndByTypeTransaction
     * @author: (VNEXT) ChinhTQ
     * @param typeId
     * @return List<UserTransaction>
     * @throws ApiValidateException
     */
    public List<UserTransaction> getTransactionByUserAndByTypeTransaction(Integer typeId) throws ApiValidateException;

    /**
     * csvWriterByUser
     * @author: (VNEXT) ChinhTQ
     * @throws ApiValidateException
     */
    public void csvWriterByUser(List<UserTransaction> userTransactions) throws ApiValidateException;

}
