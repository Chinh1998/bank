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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bean.ResultBean;
import com.example.demo.bean.entity.BankEntity;
import com.example.demo.bean.entity.UserEntity;
import com.example.demo.bean.model.JwtResponse;
import com.example.demo.bean.model.UserBank;
import com.example.demo.service.UserService;
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
public class UserController {

    private static final Log log = LogFactory.getLog(UserController.class);

    @Autowired
    private UserService userService;;

    /**
     * createUser
     * @author: (VNEXT) ChinhTQ
     * @param json
     * @return
     */
    @RequestMapping(value = "/api/user/register", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> createUser(@RequestBody String json) {
        log.debug("### createUser START ###");
        UserEntity user = null;
        ResultBean resultBean = null;
        try {
            user = userService.createUser(json);
        } catch (ApiValidateException e) {
            resultBean = new ResultBean(e.getCode(), e.getField(), e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.METHOD_NOT_ALLOWED);
        }
        resultBean = new ResultBean(user, "200", "...", "Thành công");
        log.debug("### createUser END ###");
        return new ResponseEntity<ResultBean>(resultBean, HttpStatus.OK);
    }

    /**
     * getUserById
     * @author: (VNEXT) ChinhTQ
     * @return
     */
    @RequestMapping(value = "/api/user/get_info", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> getUserById() {
        log.debug("### getUserById START ###");
        UserEntity user = null;
        ResultBean resultBean = null;
        try {
            user = userService.getUserById();
        } catch (ApiValidateException e) {
            resultBean = new ResultBean(e.getCode(), e.getField(), e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.FORBIDDEN);
        }
        resultBean = new ResultBean(user, "200", user.getUserId().toString(), "OK");
        log.debug("### getUserById END ###");
        return new ResponseEntity<ResultBean>(resultBean, HttpStatus.OK);
    }

    /**
     * updateUserById
     * @author: (VNEXT) ChinhTQ
     * @param json
     * @return
     */
    @RequestMapping(value = "/api/user/update", method = RequestMethod.PUT, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> updateUser(@RequestBody String json) {
        log.debug("### updateUser START ###");
        UserEntity user = null;
        ResultBean resultBean = null;
        try {
            user = userService.updateUser(json);
        } catch (ApiValidateException e) {
            resultBean = new ResultBean(e.getCode(), e.getField(), e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            resultBean = new ResultBean("", e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_REQUEST);
        }
        resultBean = new ResultBean(user, "200", "...", "Thành công");
        log.debug("### updateUser END ###");
        return new ResponseEntity<ResultBean>(resultBean, HttpStatus.OK);
    }

    /**
     * linkToBank
     * @author: (VNEXT) ChinhTQ
     * @param json
     * @return
     */
    @RequestMapping(value = "/api/user/linktobank", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> linkToBank(@RequestBody String json) {
        log.debug("### linkToBank START ###");
        BankEntity userBankEntity = null;
        ResultBean resultBean = null;
        try {
            userBankEntity = userService.linkToBank(json);
        } catch (ApiValidateException e) {
            resultBean = new ResultBean(e.getCode(), e.getField(), e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_REQUEST);
        }
        resultBean = new ResultBean(userBankEntity, "200", "...", "Thành công");
        log.debug("### linkToBank END ###");
        return new ResponseEntity<ResultBean>(resultBean, HttpStatus.OK);
    }

    /**
     * getBanksByUserId
     * @author: (VNEXT) ChinhTQ
     * @return
     */
    @RequestMapping(value = "/api/user/listbank", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> getBanksByUser() {
        log.debug("### getBanksByUser START ###");
        List<UserBank> userBanks = null;
        ResultBean resultBean = null;
        try {
            userBanks = userService.getBanksByUser();
        } catch (ApiValidateException e) {
            resultBean = new ResultBean(e.getCode(), e.getField(), e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_REQUEST);
        }
        resultBean = new ResultBean(userBanks, "200", "getBanksByUserId", "OK");
        log.debug("### getBanksByUser END ###");
        return new ResponseEntity<ResultBean>(resultBean, HttpStatus.OK);
    }

    /**
     * login
     * @author: (VNEXT) ChinhTQ
     * @param loginForm
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/api/user/login", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> login(@RequestBody String json) {
        log.debug("### login START ###");
        JwtResponse jwtResponse = null;
        ResultBean resultBean = null;
        try {
            jwtResponse = userService.login(json);
        } catch (ApiValidateException e) {
            resultBean = new ResultBean(e.getCode(), e.getField(), e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            resultBean = new ResultBean("401", e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.UNAUTHORIZED);
        }
        resultBean = new ResultBean(jwtResponse, "200", "login", "OK");
        log.debug("### login END ###");
        return new ResponseEntity<ResultBean>(resultBean, HttpStatus.OK);
    }

}
