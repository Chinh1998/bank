/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package com.example.demo.dao;

import java.util.List;

import com.example.demo.bean.entity.BankEntity;

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
public interface BankDao {

    /**
     * createBank
     * @author: (VNEXT) ChinhTQ
     * @param bankEntity
     */
    public void createBank(BankEntity bankEntity);

    /**
     * getAll
     * @author: (VNEXT) ChinhTQ
     * @return list bank
     */
    public List<BankEntity> getAll();

    /**
     * getById
     * @author: (VNEXT) ChinhTQ
     * @param id
     * @return BankEntity
     */
    public BankEntity getById(Integer id);

}
