/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package com.example.demo.service;

import com.example.demo.bean.ResultBean;
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

public interface BankService {

    /**
     * createBank
     * @author: (VNEXT) ChinhTQ
     * @param json
     * @return BankEntity
     * @throws ApiValidateException
     */
    public ResultBean createBank(String json) throws ApiValidateException;

    /**
     * getAll
     * @author: (VNEXT) ChinhTQ
     * @return List<BankEntity>
     * @throws ApiValidateException 
     */
    public ResultBean getAll() throws ApiValidateException;

    /**
     * getById
     * @author: (VNEXT) ChinhTQ
     * @param id
     * @return BankEntity
     * @throws ApiValidateException 
     */
    public ResultBean getBankById(Integer id) throws ApiValidateException;
}
