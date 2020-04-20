/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package com.example.demo.controller;

import java.util.List;

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
import com.example.demo.bean.entity.BankEntity;
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
@RestController
public class BankController {

    @Autowired
    private BankService bankService;

    /**
     * createBank
     * @author: (VNEXT) ChinhTQ
     * @param json
     * @return
     */
    @RequestMapping(value = "/api/bank/create", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> createBank(@RequestBody String json) {
        BankEntity bank = null;
        ResultBean resultBean = null;
        try {
            bank = bankService.createBank(json);
        } catch (ApiValidateException e) {
            resultBean = new ResultBean(e.getCode(), e.getField(), e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_REQUEST);
        }
        resultBean = new ResultBean(bank, "200", "...", "Thành công");
        return new ResponseEntity<ResultBean>(resultBean, HttpStatus.OK);
    }

    /**
     * getAllBank
     * @author: (VNEXT) ChinhTQ
     * @return
     */
    @RequestMapping(value = "/api/bank/all", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> getAllBank() {
        List<BankEntity> bankEntities = null;
        ResultBean resultBean = null;
        try {
            bankEntities = bankService.getAll();
        } catch (ApiValidateException e) {
            resultBean = new ResultBean(e.getCode(), e.getField(), e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_REQUEST);
        }
        resultBean = new ResultBean(bankEntities, "200", "...", "Thành công");
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
        BankEntity bankEntity = null;
        ResultBean resultBean = null;
        try {
            bankEntity = bankService.getBankById(id);
        } catch (ApiValidateException e) {
            resultBean = new ResultBean(e.getCode(), e.getField(), e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_REQUEST);
        }
        resultBean = new ResultBean(bankEntity, "200", "...", "Thành công");
        return new ResponseEntity<ResultBean>(resultBean, HttpStatus.OK);
    }
}
