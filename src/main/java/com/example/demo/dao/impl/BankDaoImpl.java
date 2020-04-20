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

import com.example.demo.bean.entity.BankEntity;
import com.example.demo.dao.BankDao;

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
public class BankDaoImpl implements BankDao {
    private static final Log log = LogFactory.getLog(BankDaoImpl.class);
    @Autowired
    private EntityManager entityManager;

    /**
     * createBank
     * @author: (VNEXT) ChinhTQ
     * @param bankEntity
     */
    @Override
    public void createBank(BankEntity bankEntity) {
        log.debug("### createBank START ###");
        entityManager.persist(bankEntity);
        log.debug("### createBank END ###");
    }

    /**
     * getAll
     * @author: (VNEXT) ChinhTQ
     * @return list bank
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<BankEntity> getAll() {
        log.debug("### getAll START ###");
        StringBuilder sql = new StringBuilder();
        sql.append(" FROM ");
        sql.append(" BankEntity ");

        Query query = this.entityManager.createQuery(sql.toString());

        List<BankEntity> bankEntities = query.getResultList();
        log.debug("### getAll END ###");
        return bankEntities;
    }

    @Override
    public BankEntity getById(Integer id) {
        log.debug("### getById START ###");
        StringBuilder sql = new StringBuilder();
        sql.append(" FROM ");
        sql.append("    BankEntity b ");
        sql.append(" WHERE ");
        sql.append("    b.bankId = :id");

        Query query = this.entityManager.createQuery(sql.toString());
        query.setParameter("id", id);

        BankEntity bankEntity = (BankEntity) query.getSingleResult();
        log.debug("### getById END ###");
        return bankEntity;
    }

}
