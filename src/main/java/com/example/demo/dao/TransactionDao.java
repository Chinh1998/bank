/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package com.example.demo.dao;

import java.util.List;

import com.example.demo.bean.entity.TransactionEntity;
import com.example.demo.bean.model.UserTransaction;

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
public interface TransactionDao {

    /**
     * createTransaction
     * @author: (VNEXT) ChinhTQ
     * @param transactionEntity
     */
    public void createTransaction(TransactionEntity transactionEntity);

    /**
     * getByUserId
     * @author: (VNEXT) ChinhTQ
     * @param userId
     * @return list transaction of one user
     */
    public List<UserTransaction> getByUserId(Integer userId);

    /**
     * getTransactionByUserIdAndByTypeTransaction
     * @author: (VNEXT) ChinhTQ
     * @param userId
     * @param typeId
     * @return List<UserTransaction>
     */
    public List<UserTransaction> getTransactionByUserIdAndByTypeTransaction(Integer userId, Integer typeId);
}
