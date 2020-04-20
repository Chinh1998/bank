/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package com.example.demo.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.bean.entity.BankEntity;
import com.example.demo.dao.BankDao;
import com.example.demo.service.BankService;
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
@Service
public class BankServiceImpl implements BankService {
    private static final Log log = LogFactory.getLog(BankServiceImpl.class);
    @Autowired
    private BankDao bankDao;

    /**
     * createBank
     * @author: (VNEXT) ChinhTQ
     * @param json
     * @return BankEntity
     * @throws ApiValidateException
     */
    @Override
    public BankEntity createBank(String json) throws ApiValidateException {
        log.debug("### createBank START ###");
        JSONObject jsonObject = new JSONObject(json);
        if (jsonObject.isEmpty()) {
            throw new ApiValidateException("404", "createBank", "Lỗi");
        }
        BankEntity bankEntity = new BankEntity();
        bankEntity.setBankName(jsonObject.getString("bankName"));
        bankDao.createBank(bankEntity);
        log.debug("### createBank END ###");
        return bankEntity;
    }

    /**
     * getAll
     * @author: (VNEXT) ChinhTQ
     * @return List<BankEntity>
     * @throws ApiValidateException 
     */
    @Override
    public List<BankEntity> getAll() throws ApiValidateException {
        log.debug("### getAll START ###");
        List<BankEntity> entities = bankDao.getAll();
        if (entities.isEmpty()) {
            throw new ApiValidateException("404", "...", "not found");
        }
        log.debug("### getAll END ###");
        return entities;
    }

    /**
     * getById
     * @author: (VNEXT) ChinhTQ
     * @param id
     * @return BankEntity
     * @throws ApiValidateException 
     */
    @Override
    public BankEntity getBankById(Integer id) throws ApiValidateException {
        log.debug("### getBankById START ###");
        BankEntity bankEntity = bankDao.getById(id);
        if (bankEntity == null) {
            throw new ApiValidateException("404", "...", "not found");
        }
        log.debug("### getBankById END ###");
        return bankEntity;
    }

}
