/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package com.example.demo.controller;

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
import com.example.demo.service.BankService;
import com.example.demo.ultil.ApiValidateException;
import com.example.demo.ultil.Constant;

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
public class BankController {
    private static final Logger log = Logger.getLogger(BankController.class);
    @Autowired
    private BankService bankService;
    static final Constant constant = new Constant();

    /**
     * createBank
     * @author: (VNEXT) ChinhTQ
     * @param json
     * @return
     */
    @RequestMapping(value = "/api/bank/create", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> createBank(@RequestBody String json) {
        log.debug("### createBank START ###");
        ResultBean resultBean = null;
        try {
            resultBean = bankService.createBank(json);
        } catch (ApiValidateException e) {
            resultBean = new ResultBean(e.getCode(), e.getField(), e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_REQUEST);
        }
        log.debug("### createBank END ###");
        return new ResponseEntity<ResultBean>(resultBean, HttpStatus.OK);
    }

    /**
     * getAllBank
     * @author: (VNEXT) ChinhTQ
     * @return
     */
    @RequestMapping(value = "/api/bank/all", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> getAllBank() {
        log.debug("### getAllBank START ###");
        ResultBean resultBean = null;
        try {
            resultBean = bankService.getAll();
        } catch (ApiValidateException e) {
            resultBean = new ResultBean(e.getCode(), e.getField(), e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_REQUEST);
        }
        log.debug("### getAllBank END ###");
        return new ResponseEntity<ResultBean>(resultBean, HttpStatus.OK);
    }

    /**
     * getBankById
     * @author: (VNEXT) ChinhTQ
     * @param id
     * @return
     */
    @RequestMapping(value = "/api/bank/{id}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> getBankById(@PathVariable Integer id) {
        log.debug("### getBankById START ###");
        ResultBean resultBean = null;
        try {
            resultBean = bankService.getBankById(id);
        } catch (ApiValidateException e) {
            resultBean = new ResultBean(e.getCode(), e.getField(), e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_REQUEST);
        }
        log.debug("### getBankById END ###");
        return new ResponseEntity<ResultBean>(resultBean, HttpStatus.OK);
    }
}
