/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package com.example.demo.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.bean.entity.TransactionEntity;
import com.example.demo.bean.model.UserTransaction;
import com.example.demo.dao.TransactionDao;

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
@Repository
@Transactional
public class TransactionDaoImpl implements TransactionDao {
    private static final Log log = LogFactory.getLog(TransactionDaoImpl.class);

    @Autowired
    private EntityManager entityManager;

    /**
     * createTransaction
     * @author: (VNEXT) ChinhTQ
     * @param transactionEntity
     */
    @Override
    public void createTransaction(TransactionEntity transactionEntity) {
        log.debug("### createTransaction START ###");
        entityManager.persist(transactionEntity);
        log.debug("### createTransaction END ###");
    }

    /**
     * getByUserId
     * @author: (VNEXT) ChinhTQ
     * @param userId
     * @return list transaction of one user
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<UserTransaction> getByUserId(Integer createBy) {
        log.debug("### getByUserId START ###");
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT new com.example.demo.bean.model.UserTransaction(");
        sql.append("  be.bankName, ");
        sql.append("  tpe.typeTrading, ");
        sql.append("  ts.dateTrading, ");
        sql.append("  ts.money,");
        sql.append("  ts.fee )");
        sql.append(" FROM ");
        sql.append("    BankEntity be ");
        sql.append(" JOIN ");
        sql.append("    TransactionEntity ts ");
        sql.append(" ON ");
        sql.append("    ts.bankId = be.bankId ");
        sql.append(" JOIN ");
        sql.append("    TypeTransactionEntity tpe ");
        sql.append(" ON ");
        sql.append("    ts.typeId = tpe.typeId ");
        sql.append(" WHERE ");
        sql.append("	ts.createBy = :createBy ");

        Query query = this.entityManager.createQuery(sql.toString());
        query.setParameter("createBy", createBy);

        List<UserTransaction> userTransactions = query.getResultList();
        log.debug("### getByUserId END ###");
        return userTransactions;
    }

    /**
     * getTransactionByUserIdAndByTypeTransaction
     * @author: (VNEXT) ChinhTQ
     * @param userId
     * @param typeId
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<UserTransaction> getTransactionByUserIdAndByTypeTransaction(Integer userId, Integer typeId) {
        log.debug("### getTransactionByUserIdAndByTypeTransaction START ###");
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT new com.example.demo.bean.model.UserTransaction(");
        sql.append("  be.bankName, ");
        sql.append("  tpe.typeTrading, ");
        sql.append("  ts.dateTrading, ");
        sql.append("  ts.money, ");
        sql.append("  ts.fee )");
        sql.append(" FROM ");
        sql.append("    BankEntity be ");
        sql.append(" JOIN ");
        sql.append("    TransactionEntity ts ");
        sql.append(" ON ");
        sql.append("    ts.bankId = be.bankId ");
        sql.append(" JOIN ");
        sql.append("    TypeTransactionEntity tpe ");
        sql.append(" ON ");
        sql.append("    ts.typeId = tpe.typeId ");
        sql.append(" WHERE ");
        sql.append("    ts.createBy = :userId ");
        sql.append(" AND ");
        sql.append("    tpe.typeId = :typeId ");

        Query query = this.entityManager.createQuery(sql.toString());
        query.setParameter("userId", userId);
        query.setParameter("typeId", typeId);

        List<UserTransaction> userTransactions = query.getResultList();
        log.debug("### getTransactionByUserIdAndByTypeTransaction END ###");
        return userTransactions;
    }
}
