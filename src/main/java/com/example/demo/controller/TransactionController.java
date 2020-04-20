/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package com.example.demo.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
    private static final Log log = LogFactory.getLog(TransactionController.class);
    @Autowired
    private TransactionService transactionService;

    /**
     * sendMoney
     * @author: (VNEXT) ChinhTQ
     * @param json
     * @return 
     */
    @RequestMapping(value = "/api/transaction/send", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> sendMoney(@RequestBody String json) {
        log.debug("### sendMoney START ###");
        TransactionEntity transactionEntity = null;
        try {
            transactionEntity = transactionService.sendMoney(json);
        } catch (ApiValidateException e) {
            ResultBean resultBean = new ResultBean(e.getCode(), e.getField(), e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_GATEWAY);
        }
        ResultBean resultBean = new ResultBean(transactionEntity, "200", "...", "Nộp thành công");
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
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_GATEWAY);
        } catch (Exception e) {
            ResultBean resultBean = new ResultBean("400", e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_GATEWAY);
        }
        ResultBean resultBean = new ResultBean(transactionEntity, "200", "...", "Rút thành công");
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
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_GATEWAY);
        } catch (Exception e) {
            ResultBean resultBean = new ResultBean("400", e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_GATEWAY);
        }
        ResultBean resultBean = new ResultBean(transactionEntity, "200", "...", "Chuyển thành công");
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
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.NOT_FOUND);
        }
        resultBean = new ResultBean(userTransactions, "200", "OK");
        log.debug("### getAllTransactionByUser END ###");
        return new ResponseEntity<ResultBean>(resultBean, HttpStatus.OK);
    }

    /**
     * csvTransactionByUserAndByTypeTransaction
     * @author: (VNEXT) ChinhTQ
     * @param id
     * @return
     */
    @RequestMapping(value = "/api/transactions/type-transaction/{id}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> getTransactionByUserAndByTypeTransaction(@PathVariable Integer id) {
        log.debug("### getTransactionByUserAndByTypeTransaction START ###");
        List<UserTransaction> userTransactions = null;
        try {
            userTransactions = transactionService.getTransactionByUserAndByTypeTransaction(id);
        } catch (ApiValidateException e) {
            ResultBean resultBean = new ResultBean(e.getCode(), e.getField(), e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_GATEWAY);
        } catch (Exception e) {
            ResultBean resultBean = new ResultBean("400", e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_GATEWAY);
        }
        ResultBean resultBean = new ResultBean(userTransactions, "200", "...", "OK");
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
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_GATEWAY);
        } catch (Exception e) {
            ResultBean resultBean = new ResultBean("400", e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_GATEWAY);
        }
        ResultBean resultBean = new ResultBean(userTransactions, "200", "writer csv", "Ghi và tải thành công");
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
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_GATEWAY);
        } catch (Exception e) {
            ResultBean resultBean = new ResultBean("400", e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_GATEWAY);
        }
        ResultBean resultBean = new ResultBean(userTransactions, "200", "writer csv", "Ghi và tải thành công");
        log.debug("### csvWriterByUser END ###");
        return new ResponseEntity<ResultBean>(resultBean, HttpStatus.OK);
    }

}
