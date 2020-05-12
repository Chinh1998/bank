/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package com.example.demo.service.impl;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.bean.ResultBean;
import com.example.demo.bean.entity.BankEntity;
import com.example.demo.dao.BankDao;
import com.example.demo.service.BankService;
import com.example.demo.ultil.ApiValidateException;
import com.example.demo.ultil.Constant;
import com.example.demo.ultil.ReadProperties;

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
    private static final Logger log = Logger.getLogger(BankServiceImpl.class);
    @Autowired
    private BankDao bankDao;
    @SuppressWarnings("static-access")
    private Properties properties = new ReadProperties().readProperties();
    static final Constant constant = new Constant();

    /**
     * createBank
     * @author: (VNEXT) ChinhTQ
     * @param json
     * @return BankEntity
     * @throws ApiValidateException
     */
    @SuppressWarnings("static-access")
    @Override
    public ResultBean createBank(String json) throws ApiValidateException {
        log.debug("### createBank START ###");
        JSONObject jsonObject = new JSONObject(json);
        if (jsonObject.isEmpty()) {
            throw new ApiValidateException(constant.NOT_FOUND, "createBank", properties.getProperty("inputFaild"));
        }
        BankEntity bankEntity = new BankEntity();
        bankEntity.setBankName(jsonObject.getString("bankName"));
        bankDao.createBank(bankEntity);
        ResultBean resultBean = new ResultBean(bankEntity, Constant.OK, "...", properties.getProperty("ok"));
        log.debug("### createBank END ###");
        return resultBean;
    }

    /**
     * getAll
     * @author: (VNEXT) ChinhTQ
     * @return List<BankEntity>
     * @throws ApiValidateException 
     */
    @SuppressWarnings("static-access")
    @Override
    public ResultBean getAll() throws ApiValidateException {
        log.debug("### getAll START ###");
        List<BankEntity> entities = bankDao.getAll();
        if (entities.isEmpty()) {
            throw new ApiValidateException(constant.NOT_FOUND, "...", properties.getProperty("inputFaild"));
        }
        log.debug("### getAll END ###");
        ResultBean resultBean = new ResultBean(entities, constant.OK, properties.getProperty("ok"));
        return resultBean;
    }

    /**
     * getById
     * @author: (VNEXT) ChinhTQ
     * @param id
     * @return BankEntity
     * @throws ApiValidateException 
     */
    @SuppressWarnings("static-access")
    @Override
    public ResultBean getBankById(Integer id) throws ApiValidateException {
        log.debug("### getBankById START ###");
        BankEntity bankEntity = bankDao.getById(id);
        if (bankEntity == null) {
            throw new ApiValidateException(constant.NOT_FOUND, "...", properties.getProperty("inputFaild"));
        }
        ResultBean resultBean = new ResultBean(bankEntity, Constant.OK, "...", properties.getProperty("ok"));
        log.debug("### getBankById END ###");
        return resultBean;
    }

}
