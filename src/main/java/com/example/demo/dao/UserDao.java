/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package com.example.demo.dao;

import java.util.List;

import com.example.demo.bean.entity.UserBankEntity;
import com.example.demo.bean.entity.UserEntity;
import com.example.demo.bean.model.UserBank;
import com.example.demo.bean.model.UserDto;

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
public interface UserDao {

    /**
     * createUser
     * @author: (VNEXT) ChinhTQ
     * @param user
     */
    public void createUser(UserEntity user);

    /**
     * getUserById
     * @author: (VNEXT) ChinhTQ
     * @param id
     * @return UserEntity
     */
    public UserEntity getUserById(Integer id);

    /**
     * getUserDtoByUserId
     * @author: (VNEXT) ChinhTQ
     * @param id
     * @return
     */
    public UserDto getUserDtoByUserId(Integer id);

    /**
     * updateUser
     * @author: (VNEXT) ChinhTQ
     * @param user
     */
    public void updateUser(UserEntity user);

    /**
     * afterSendMoney
     * @author: (VNEXT) ChinhTQ
     * @param userId
     * @param money
     */
    public void afterSendMoney(Integer userId, double money);

    /**
     * afterWithdrawMoney
     * @author: (VNEXT) ChinhTQ
     * @param userId
     * @param money
     */
    public void afterWithdrawMoney(Integer userId, double money, double fee);

    /**
     * afterTransferMoney
     * @author: (VNEXT) ChinhTQ
     * @param userTransfer
     * @param money
     * @return true:success
     */
    public boolean afterTransferMoney(Integer userTransfer, double money, double fee);

    /**
     * afterReceiverMoney
     * @author: (VNEXT) ChinhTQ
     * @param userReceiver
     * @param money
     * @return true:success
     */
    public void afterReceiverMoney(Integer userReceiver, double money);

    /**
     * linkToBank
     * @author: (VNEXT) ChinhTQ
     * @param userBankEntity
     */
    public void linkToBank(UserBankEntity userBankEntity);

    /**
     * getAllBankByUserId
     * @author: (VNEXT) ChinhTQ
     * @param id
     * @return List<UserBankEntity> of one user
     */
    public List<UserBankEntity> getAllBankByUserId(Integer id);

    /**
     * getBanksByUserId
     * @author: (VNEXT) ChinhTQ
     * @param id
     * @return List<UserBank> of one user
     */
    public List<UserBank> getBanksByUserId(Integer id);

    /**
     * getByUsername
     * @author: (VNEXT) ChinhTQ
     * @param username
     * @return UserEntity
     */
    public UserEntity getByUsername(String username);

    //    /**
    //     * getRolesByUserId
    //     * @author: (VNEXT) ChinhTQ
    //     * @param id
    //     * @return Map<RoleEntity>
    //     */
    //    public Set<RoleEntity> getRolesByUserId(Integer id);
}
