/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package com.example.demo.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.bean.entity.UserBankEntity;
import com.example.demo.bean.entity.UserEntity;
import com.example.demo.bean.model.UserBank;
import com.example.demo.bean.model.UserDto;
import com.example.demo.dao.UserDao;

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
public class UserDaoImpl implements UserDao {
    private static final Logger log = Logger.getLogger(UserDaoImpl.class);

    @Autowired
    private EntityManager etityManager;

    /**
     * createUser
     * @author: (VNEXT) ChinhTQ
     * @param user
     */
    @Override
    public void createUser(UserEntity user) {
        log.debug("### createUser START ###");
        etityManager.persist(user);
        log.debug("### createUser END ###");
    }

    /**
     * getUserById
     * @author: (VNEXT) ChinhTQ
     * @param id
     * @return UserEntity
     */
    @Override
    public UserEntity getUserById(Integer id) {
        log.debug("### getUserById START ###");
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT u ");
        sql.append(" FROM ");
        sql.append("    UserEntity u ");
        sql.append(" WHERE ");
        sql.append("    u.userId = :id ");

        Query query = this.etityManager.createQuery(sql.toString());
        query.setParameter("id", id);
        UserEntity user = null;
        try {
            user = (UserEntity) query.getSingleResult();
        } catch (NoResultException e) {
            // TODO: handle exception
        }
        log.debug("### getUserById END ###");
        return user;
    }
    @Override
    public UserDto getUserDtoByUserId(Integer id) {
        log.debug("### getUserById START ###");
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT new com.example.demo.bean.model.UserDto(  ");
        sql.append("    u.username,  ");
        sql.append("    u.phoneNumber,  ");
        sql.append("    u.email,  ");
        sql.append("    u.dOBirth,  ");
        sql.append("    u.balance,  ");
        sql.append("    b.bankName )");
        sql.append(" FROM ");
        sql.append("    UserEntity u ");
        sql.append(" INNER JOIN ");
        sql.append("    BankEntity b ");
        sql.append(" ON ");
        sql.append("    u.bankId = b.bankId ");
        sql.append(" WHERE ");
        sql.append("    u.userId = :id ");

        Query query = this.etityManager.createQuery(sql.toString());
        query.setParameter("id", id);
        UserDto user = null;
        try {
            user = (UserDto) query.getSingleResult();
        } catch (NoResultException e) {
            // TODO: handle exception
        }
        log.debug("### getUserById END ###");
        return user;
    }
    /**
     * updateUser
     * @author: (VNEXT) ChinhTQ
     * @param user
     */
    @Override
    public void updateUser(UserEntity user) {
        log.debug("### updateUser START ###");
        this.etityManager.merge(user);
        log.debug("### updateUser END ###");
    }

    /**
     * afterSendMoney
     * @author: (VNEXT) ChinhTQ
     * @param userId
     * @param money
     */
    @Override
    public void afterSendMoney(Integer userId, double money) {
        log.debug("### afterSendMoney START ###");
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE ");
        sql.append(" UserEntity u");
        sql.append(" SET ");
        sql.append(" u.balance = u.balance + :money ");
        sql.append(" WHERE ");
        sql.append("	u.userId = :userId ");

        Query query = this.etityManager.createQuery(sql.toString());

        query.setParameter("money", money);
        query.setParameter("userId", userId);

        query.executeUpdate();
        log.debug("### afterSendMoney END ###");
    }

    /**
     * afterWithdrawMoney
     * @author: (VNEXT) ChinhTQ
     * @param userId
     * @param money
     */
    @Override
    public void afterWithdrawMoney(Integer userId, double money, double fee) {
        log.debug("### afterWithdrawMoney START ###");
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE ");
        sql.append(" UserEntity u");
        sql.append(" SET ");
        sql.append(" u.balance = u.balance - :money - :fee ");
        sql.append(" WHERE ");
        sql.append("	u.userId = :userId ");

        Query query = this.etityManager.createQuery(sql.toString());
        query.setParameter("money", money);
        query.setParameter("userId", userId);
        query.setParameter("fee", fee);
        query.executeUpdate();
        log.debug("### afterWithdrawMoney END ###");
    }

    /**
     * afterTransferMoney
     * @author: (VNEXT) ChinhTQ
     * @param userTransfer
     * @param money
     * @return true:success
     */
    @Override
    public boolean afterTransferMoney(Integer userTransfer, double money, double fee) {
        log.debug("### afterTransferMoney START ###");
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE ");
        sql.append(" UserEntity u");
        sql.append(" SET ");
        sql.append(" u.balance = u.balance - :money - :fee ");
        sql.append(" WHERE ");
        sql.append("    u.userId = :userTransfer ");

        Query query = this.etityManager.createQuery(sql.toString());
        query.setParameter("money", money);
        query.setParameter("userTransfer", userTransfer);
        query.setParameter("fee", fee);
        int execute = query.executeUpdate();
        if (execute == 1) {
            return true;
        }
        log.debug("### afterTransferMoney END ###");
        return false;
    }

    /**
     * afterReceiverMoney
     * @author: (VNEXT) ChinhTQ
     * @param userReceiver
     * @param money
     * @return true:success
     */
    @Override
    public void afterReceiverMoney(Integer userReceiver, double money) {
        log.debug("### afterReceiverMoney START ###");
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE ");
        sql.append(" UserEntity u");
        sql.append(" SET ");
        sql.append(" u.balance = u.balance + :money ");
        sql.append(" WHERE ");
        sql.append("    u.userId = :userReceiver ");

        Query query = this.etityManager.createQuery(sql.toString());
        query.setParameter("userReceiver", userReceiver);
        query.setParameter("money", money);
        query.executeUpdate();
        log.debug("### afterReceiverMoney END ###");
    }

    /**
     * linkToBank
     * @author: (VNEXT) ChinhTQ
     * @param userBankEntity
     */
    @Override
    public void linkToBank(UserBankEntity userBankEntity) {
        log.debug("### linkToBank START ###");
        this.etityManager.persist(userBankEntity);
        log.debug("### linkToBank END ###");
    }

    /**
     * getAllBankByUserId
     * @author: (VNEXT) ChinhTQ
     * @param id
     * @return List<UserBankEntity> of one user
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<UserBankEntity> getAllBankByUserId(Integer id) {
        log.debug("### getAllBankByUserId START ###");
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ub ");
        sql.append(" FROM ");
        sql.append("    UserBankEntity ub ");
        sql.append(" WHERE ");
        sql.append("     ub.userId = :id");

        Query query = this.etityManager.createQuery(sql.toString());
        query.setParameter("id", id);

        List<UserBankEntity> userBankEntities = query.getResultList();
        log.debug("### getAllBankByUserId END ###");
        return userBankEntities;
    }

    /**
     * getBanksByUserId
     * @author: (VNEXT) ChinhTQ
     * @param id
     * @return List<UserBank> of one user
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<UserBank> getBanksByUserId(Integer id) {
        log.debug("### getBanksByUserId START ###");
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT new com.example.demo.bean.model.UserBank( ");
        sql.append(" be.bankId, ");
        sql.append(" be.bankName )");
        sql.append(" FROM ");
        sql.append("    BankEntity be ");
        sql.append(" JOIN ");
        sql.append("    UserBankEntity ub ");
        sql.append(" ON ");
        sql.append("    be.bankId = ub.bankId ");
        sql.append(" WHERE ");
        sql.append("    ub.userId = :id");

        Query query = this.etityManager.createQuery(sql.toString());
        query.setParameter("id", id);

        List<UserBank> userBanks = query.getResultList();
        log.debug("### getBanksByUserId END ###");
        return userBanks;
    }

    /**
     * getByUsername
     * @author: (VNEXT) ChinhTQ
     * @param username
     * @return UserEntity
     */
    @Override
    public UserEntity getByUsername(String username) {
        log.debug("### getByUsername START ###");
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ub ");
        sql.append(" FROM ");
        sql.append("    UserEntity ue ");
        sql.append(" WHERE ");
        sql.append("     ue.userName =?");

        Query query = this.etityManager.createQuery(sql.toString());
        query.setParameter(1, username);

        UserEntity userEntity = (UserEntity) query.getSingleResult();
        log.debug("### getByUsername END ###");
        return userEntity;
    }
}
