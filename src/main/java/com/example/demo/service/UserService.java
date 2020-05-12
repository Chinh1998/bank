/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package com.example.demo.service;

import java.util.List;

import com.example.demo.bean.ResultBean;
import com.example.demo.bean.entity.UserBankEntity;
import com.example.demo.bean.entity.UserEntity;
import com.example.demo.bean.model.JwtResponse;
import com.example.demo.bean.model.UserBank;
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

public interface UserService {

    /**
     * createUser
     * @author: (VNEXT) ChinhTQ
     * @param json
     * @return UserEntity
     * @throws ApiValidateException
     */
    public ResultBean createUser(String json) throws ApiValidateException;

    /**
     * getUserById
     * @author: (VNEXT) ChinhTQ
     * @param id
     * @return UserEntity
     * @throws ApiValidateException
     */
    public ResultBean getUserById() throws ApiValidateException;

    /**
     * updateUserById
     * @author: (VNEXT) ChinhTQ
     * @param json
     * @param Id
     * @return UserEntity
     * @throws ApiValidateException
     */
    public ResultBean updateUser(String json) throws ApiValidateException;

    /**
     * linkToBank
     * @author: (VNEXT) ChinhTQ
     * @param id
     * @param json
     * @return UserBankEntity
     * @throws ApiValidateException
     */
    public ResultBean linkToBank(String json) throws ApiValidateException;

    /**
     * getAllBankByUserId
     * @author: (VNEXT) ChinhTQ
     * @param id
     * @return List<UserBankEntity> of one user
     * @throws ApiValidateException 
     */
    public List<UserBankEntity> getAllBankByUser() throws ApiValidateException;

    /**
     * getBanksByUserId
     * @author: (VNEXT) ChinhTQ
     * @param id
     * @return List<UserBank> of one user
     * @throws ApiValidateException 
     */
    public List<UserBank> getBanksByUser() throws ApiValidateException;

    /**
     * login
     * @author: (VNEXT) ChinhTQ
     * @param json
     * @return JwtResponse
     * @throws ApiValidateException
     * @throws Exception
     */
    public JwtResponse login(String json) throws ApiValidateException, Exception;

    /**
     * authenticate
     * @author: (VNEXT) ChinhTQ
     * @param username
     * @param password
     * @throws Exception
     */
    public void authenticate(String username, String password) throws Exception;

    /**
     * getByUsername
     * @author: (VNEXT) ChinhTQ
     * @param username
     * @return UserEntity
     * @throws ApiValidateException 
     */
    public UserEntity getByUsername(String username) throws ApiValidateException;
}
