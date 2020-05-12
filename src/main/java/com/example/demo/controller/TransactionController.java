/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package com.example.demo.controller;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bean.ResultBean;
import com.example.demo.bean.entity.TransactionEntity;
import com.example.demo.bean.model.UserTransaction;
import com.example.demo.service.TransactionService;
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
@RestController
public class TransactionController {
    private static final Logger log = Logger.getLogger(TransactionController.class);
    @Autowired
    private TransactionService transactionService;
    @SuppressWarnings("static-access")
    private Properties properties = new ReadProperties().readProperties();
    static final Constant constant = new Constant();

    /**
     * sendMoney
     * @author: (VNEXT) ChinhTQ
     * @param json
     * @return 
     */
    @RequestMapping(value = "/api/transaction/send", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> sendMoney(@RequestBody String json) {
        log.debug("### sendMoney START ###");
        ResultBean resultBean;
        try {
            resultBean = transactionService.sendMoney(json);
        } catch (ApiValidateException e) {
            resultBean = new ResultBean(e.getCode(), e.getField(), e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            resultBean = new ResultBean(e.getMessage(), Constant.BAD_REQUEST);
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_REQUEST);
        }
        log.debug("### sendMoney END ###");
        return new ResponseEntity<ResultBean>(resultBean, HttpStatus.OK);
    }

    /**
     * withdrawMoney
     * @author: (VNEXT) ChinhTQ
     * @param json
     * @return
     */
    @RequestMapping(value = "/api/transaction/withdraw", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> withdrawMoney(@RequestBody String json) {
        log.debug("### withdrawMoney START ###");
        TransactionEntity transactionEntity = null;
        try {
            transactionEntity = transactionService.withdrawMoney(json);
        } catch (ApiValidateException e) {
            ResultBean resultBean = new ResultBean(e.getCode(), e.getField(), e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ResultBean resultBean = new ResultBean(Constant.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_REQUEST);
        }
        ResultBean resultBean = new ResultBean(transactionEntity, Constant.OK, "...", properties.getProperty("ok"));
        log.debug("### withdrawMoney END ###");
        return new ResponseEntity<ResultBean>(resultBean, HttpStatus.OK);
    }

    /**
     * transferMoney
     * @author: (VNEXT) ChinhTQ
     * @param json
     * @return
     */
    @RequestMapping(value = "/api/transaction/transfer", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> transferMoney(@RequestBody String json) {
        log.debug("### transferMoney START ###");
        TransactionEntity transactionEntity = null;
        try {
            transactionEntity = transactionService.transferMoney(json);
        } catch (ApiValidateException e) {
            ResultBean resultBean = new ResultBean(e.getCode(), e.getField(), e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ResultBean resultBean = new ResultBean(Constant.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_REQUEST);
        }
        ResultBean resultBean = new ResultBean(transactionEntity, Constant.OK, "...", properties.getProperty("ok"));
        log.debug("### transferMoney END ###");
        return new ResponseEntity<ResultBean>(resultBean, HttpStatus.OK);
    }

    /**
     * getAllTransactionByUserId
     * @author: (VNEXT) ChinhTQ
     * @param userId
     * @return
     */
    @RequestMapping(value = "/api/transaction/get_all", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> getAllTransactionByUser() {
        log.debug("### getAllTransactionByUser START ###");
        ResultBean resultBean = null;
        List<UserTransaction> userTransactions = null;
        try {
            userTransactions = transactionService.getAllByUser();
        } catch (ApiValidateException e) {
            resultBean = new ResultBean(e.getCode(), e.getField(), e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_REQUEST);
        }
        resultBean = new ResultBean(userTransactions, Constant.OK, properties.getProperty("ok"));
        log.debug("### getAllTransactionByUser END ###");
        return new ResponseEntity<ResultBean>(resultBean, HttpStatus.OK);
    }

    /**
     * csvTransactionByUserAndByTypeTransaction
     * @author: (VNEXT) ChinhTQ
     * @param id
     * @return
     */
    @SuppressWarnings("static-access")
    @RequestMapping(value = "/api/transactions/type-transaction/{id}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> getTransactionByUserAndByTypeTransaction(@PathVariable Integer id) {
        log.debug("### getTransactionByUserAndByTypeTransaction START ###");
        List<UserTransaction> userTransactions = null;
        try {
            userTransactions = transactionService.getTransactionByUserAndByTypeTransaction(id);
        } catch (ApiValidateException e) {
            ResultBean resultBean = new ResultBean(e.getCode(), e.getField(), e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ResultBean resultBean = new ResultBean(constant.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_REQUEST);
        }
        ResultBean resultBean = new ResultBean(userTransactions, Constant.OK, "...", properties.getProperty("ok"));
        log.debug("### getTransactionByUserAndByTypeTransaction END ###");
        return new ResponseEntity<ResultBean>(resultBean, HttpStatus.OK);
    }

    /**
     * csvTransactionByUserAndByTypeTransaction
     * @author: (VNEXT) ChinhTQ
     * @param id
     * @return
     */
    @RequestMapping(value = "/api/transactions/csv/type-transaction/{id}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> csvTransactionByUserAndByTypeTransaction(@PathVariable Integer id) {
        log.debug("### csvWriterSendTransactionByUser START ###");
        List<UserTransaction> userTransactions = null;
        try {
            userTransactions = transactionService.getTransactionByUserAndByTypeTransaction(id);
            transactionService.csvWriterByUser(userTransactions);
        } catch (ApiValidateException e) {
            ResultBean resultBean = new ResultBean(e.getCode(), e.getField(), e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ResultBean resultBean = new ResultBean("400", e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_REQUEST);
        }
        ResultBean resultBean = new ResultBean(userTransactions, Constant.OK, "writer csv", properties.getProperty("ok"));
        log.debug("### csvWriterSendTransactionByUser END ###");
        return new ResponseEntity<ResultBean>(resultBean, HttpStatus.OK);
    }

    /**
     * csvWriterByUserId
     * @author: (VNEXT) ChinhTQ
     * @param id
     * @return
     */
    @RequestMapping(value = "/api/transaction/writercsv", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> csvWriterByUser() {
        log.debug("### csvWriterByUser START ###");
        List<UserTransaction> userTransactions = null;
        try {
            userTransactions = transactionService.getAllByUser();
            transactionService.csvWriterByUser(userTransactions);
        } catch (ApiValidateException e) {
            ResultBean resultBean = new ResultBean(e.getCode(), e.getField(), e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ResultBean resultBean = new ResultBean("400", e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_REQUEST);
        }
        ResultBean resultBean = new ResultBean(userTransactions, Constant.OK, "writer csv", properties.getProperty("ok"));
        log.debug("### csvWriterByUser END ###");
        return new ResponseEntity<ResultBean>(resultBean, HttpStatus.OK);
    }

}
