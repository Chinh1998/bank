/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package com.example.demo.service.impl;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.bean.ResultBean;
import com.example.demo.bean.entity.BankEntity;
import com.example.demo.bean.entity.TransactionEntity;
import com.example.demo.bean.entity.UserEntity;
import com.example.demo.bean.model.UserTransaction;
import com.example.demo.dao.BankDao;
import com.example.demo.dao.TransactionDao;
import com.example.demo.dao.UserDao;
import com.example.demo.helper.AuthenticationHelper;
import com.example.demo.service.TransactionService;
import com.example.demo.ultil.ApiValidateException;
import com.example.demo.ultil.Constant;
import com.example.demo.ultil.ReadProperties;
import com.opencsv.CSVWriter;

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
public class TransactionServiceImpl implements TransactionService {

    private static final Logger log = Logger.getLogger(TransactionServiceImpl.class);

    @Autowired
    private TransactionDao transactionDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private BankDao bankDao;
    @Autowired
    private AuthenticationHelper authenticationHelper;
    @SuppressWarnings("static-access")
    private Properties properties = new ReadProperties().readProperties();
    static final Constant constant = new Constant();

    /**
     * sendMoney
     * @author: (VNEXT) ChinhTQ
     * @param json
     * @return TransactionEntity
     * @throws ApiValidateException
     */
    @SuppressWarnings("static-access")
    @Override
    public ResultBean sendMoney(String json) throws ApiValidateException {
        log.debug("### sendMoney START ###");
        // get user logged
        UserEntity userEntity = authenticationHelper.getLoggedInUser();
        JSONObject jsonObject = new JSONObject(json);
        //check jsonObject
        if (jsonObject.isEmpty()) {
            throw new ApiValidateException(constant.NOT_FOUND, "...", properties.getProperty("inputFaild"));
        }

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setCreateBy(userEntity.getUserId());
        transactionEntity.setUserId(userEntity.getUserId());
        transactionEntity.setBankId(userEntity.getBankId());
        transactionEntity.setTypeId(1);
        transactionEntity.setMoney(jsonObject.getDouble("money"));
        transactionEntity.setDateTrading(jsonObject.getString("dateTrading"));

        transactionDao.createTransaction(transactionEntity);
        userDao.afterSendMoney(userEntity.getUserId(), jsonObject.getDouble("money"));
        ResultBean resultBean = new ResultBean(transactionEntity, Constant.OK, "...", properties.getProperty("ok"));
        log.debug("### sendMoney END ###");
        return resultBean;

    }

    /**
     * withdrawMoney
     * @author: (VNEXT) ChinhTQ
     * @param json
     * @return TransactionEntity
     * @throws ApiValidateException
     */
    @SuppressWarnings("static-access")
    @Override
    public TransactionEntity withdrawMoney(String json) throws ApiValidateException {
        log.debug("### withdrawMoney START ###");
        JSONObject jsonObject = new JSONObject(json);
        //get user logged
        UserEntity userEntity = authenticationHelper.getLoggedInUser();
        //check jsonObject
        if (jsonObject.isEmpty()) {
            throw new ApiValidateException(constant.NOT_FOUND, "...", properties.getProperty("inputFaild"));
        }
        TransactionEntity transactionEntity = new TransactionEntity();
        double money = jsonObject.getDouble("money");

        BankEntity bankEntity = bankDao.getById(userEntity.getBankId());
        if (money % bankEntity.getMinTransaction() != 0) {
            throw new ApiValidateException("203", "withdrawMoney", properties.getProperty("inputMoney") + bankEntity.getMinTransaction());
        }
        if (Double.compare(userEntity.getBalance(), money) < 0) {
            throw new ApiValidateException("203", "withdrawMoney", properties.getProperty("checkBalance"));
        }
        transactionEntity.setCreateBy(userEntity.getUserId());
        transactionEntity.setUserId(userEntity.getUserId());
        transactionEntity.setBankId(userEntity.getBankId());
        transactionEntity.setTypeId(2);
        transactionEntity.setMoney(money);
        double fee = this.feeUse(userEntity.getUserId(), money);
        transactionEntity.setFee(fee);
        transactionEntity.setDateTrading(jsonObject.getString("dateTrading"));

        transactionDao.createTransaction(transactionEntity);
        userDao.afterWithdrawMoney(userEntity.getUserId(), jsonObject.getDouble("money"), fee);
        log.debug("### withdrawMoney END ###");
        return transactionEntity;
    }

    /**
     * transferMoney
     * @author: (VNEXT) ChinhTQ
     * @param json
     * @return TransactionEntity
     * @throws ApiValidateException
     */
    @SuppressWarnings("static-access")
    @Override
    public TransactionEntity transferMoney(String json) throws ApiValidateException {
        log.debug("### transferMoney START ###");
        JSONObject jsonObject = new JSONObject(json);
        //get user logged
        UserEntity user = authenticationHelper.getLoggedInUser();
        //check jsonObject
        if (jsonObject.isEmpty()) {
            throw new ApiValidateException(constant.NOT_FOUND, "...", properties.getProperty("inputFaild"));
        }
        TransactionEntity transactionEntity = new TransactionEntity();

        Integer userId = jsonObject.getInt("userId");
        double money = jsonObject.getDouble("money");

        // get UserEntity of user transfer

        // get BankEntity of user transfer
        BankEntity bankEntity = bankDao.getById(user.getBankId());

        // validate money
        if (money % bankEntity.getMinTransaction() != 0) {
            throw new ApiValidateException(constant.METHOD_NOT_ALLOWED, "withdrawMoney", properties.getProperty("inputMoney") + bankEntity.getMinTransaction());
        }
        if (Double.compare(user.getBalance(), money) < 0) {
            throw new ApiValidateException(constant.METHOD_NOT_ALLOWED, "withdrawMoney", properties.getProperty("checkBalance"));
        }
        transactionEntity.setCreateBy(user.getUserId());
        transactionEntity.setUserId(userId);
        transactionEntity.setBankId(user.getBankId());
        transactionEntity.setTypeId(3);
        transactionEntity.setMoney(money);
        double fee = this.feeUse(user.getUserId(), money);
        transactionEntity.setFee(fee);
        transactionEntity.setDateTrading(jsonObject.getString("dateTrading"));

        transactionDao.createTransaction(transactionEntity);
        boolean checkTranfer = userDao.afterTransferMoney(user.getUserId(), money, fee);
        if (checkTranfer == false) {
            throw new ApiValidateException(constant.INTERNAL_SERVER_ERROR, "transfer", properties.getProperty("unauthorize"));
        } else {
            userDao.afterReceiverMoney(userId, money);
        }
        log.debug("### transferMoney END ###");
        return transactionEntity;
    }

    /**
     * getUserById
     * @author: (VNEXT) ChinhTQ
     * @param userId
     * @return list transaction of one user
     * @throws ApiValidateException
     */
    @SuppressWarnings("static-access")
    @Override
    public List<UserTransaction> getAllByUser() throws ApiValidateException {
        log.debug("### getAllByUser START ###");
        //get user logged
        UserEntity user = authenticationHelper.getLoggedInUser();
        List<UserTransaction> userTransactions = transactionDao.getByUserId(user.getUserId());
        if (userTransactions.isEmpty()) {
            throw new ApiValidateException(constant.NOT_FOUND, "view", properties.getProperty("inputFaild"));
        }
        log.debug("### getAllByUser END ###");
        return userTransactions;
    }

    /**
     * csvWriterByUserId
     * @author: (VNEXT) ChinhTQ
     * @param transactions
     * @throws ApiValidateException 
     */
    @Override
    public void csvWriterByUser(List<UserTransaction> userTransactions) throws ApiValidateException {
        log.debug("### csvWriterByUser START ###");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeCreate = formatter.format(new Date());
        String csv = "src/output/transactions_" + timeCreate + ".csv";

        //get list transaction of user by id

        try {
            Writer writer = new FileWriter(csv);
            CSVWriter csvWriter = new CSVWriter(writer, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            csvWriter.writeNext(new String[] { "bankName", "dateTrading", "typeTrading", "money" });
            for (UserTransaction trans : userTransactions) {
                csvWriter.writeNext(new String[] { trans.getBankName(), trans.getTypeTrading(), trans.getDateTrading(), String.valueOf(trans.getMoney()) });
            }
            csvWriter.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        log.debug("### csvWriterByUser END ###");
    }

    /**
     * feeUse
     * @author: (VNEXT) ChinhTQ
     * @param userId
     * @param money
     * @throws ApiValidateException 
     */
    public double feeUse(Integer userId, double money) throws ApiValidateException {
        log.debug("### feeUse START ###");
        //get user logged
        UserEntity user = userDao.getUserById(userId);
        BankEntity bankEntity = bankDao.getById(user.getBankId());

        if (bankEntity.getBankName().equalsIgnoreCase("TechComBank")) {
            return 0;
        } else {
            if (Double.compare(money, 10000000) > 0) {
                return money * 0.08 / 100;
            }
            if (Double.compare(money, 5000000) >= 0)
                return 5000;
        }
        log.debug("### feeUse END ###");
        return 3000;
    }

    /**
     * getTransactionByUserAndByTypeTransaction
     * @author: (VNEXT) ChinhTQ
     * @param typeId
     * @return List<UserTransaction>
     * @throws ApiValidateException
     */
    @SuppressWarnings("static-access")
    @Override
    public List<UserTransaction> getTransactionByUserAndByTypeTransaction(Integer typeId) throws ApiValidateException {
        log.debug("### getTransactionByUserAndByTypeTransaction START ###");
        //get user logged
        UserEntity user = authenticationHelper.getLoggedInUser();
        List<UserTransaction> userTransactions = transactionDao.getTransactionByUserIdAndByTypeTransaction(user.getUserId(), typeId);
        if (userTransactions.isEmpty()) {
            throw new ApiValidateException(constant.NOT_FOUND, "view", "not found");
        }
        log.debug("### getTransactionByUserAndByTypeTransaction END ###");
        return userTransactions;
    }
}
