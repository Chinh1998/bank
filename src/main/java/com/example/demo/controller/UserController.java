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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bean.ResultBean;
import com.example.demo.bean.model.JwtResponse;
import com.example.demo.bean.model.UserBank;
import com.example.demo.service.UserService;
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
public class UserController {

    private static final Logger log = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @SuppressWarnings("static-access")
    private Properties properties = new ReadProperties().readProperties();
    static final Constant constant = new Constant();

    /**
     * createUser
     * @author: (VNEXT) ChinhTQ
     * @param json
     * @return
     */
    @RequestMapping(value = "/api/user/register", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> createUser(@RequestBody String json) {
        log.info("### createUser START ###");
        ResultBean resultBean = null;
        try {
            resultBean = userService.createUser(json);
        } catch (ApiValidateException e) {
            resultBean = new ResultBean(e.getCode(), e.getField(), e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            resultBean = new ResultBean(e.getMessage(), Constant.BAD_REQUEST);
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_REQUEST);
        }
        log.info("### createUser END ###");
        return new ResponseEntity<ResultBean>(resultBean, HttpStatus.OK);
    }

    /**
     * getUserById
     * @author: (VNEXT) ChinhTQ
     * @return
     */
    @RequestMapping(value = "/api/user/get_info", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> getUserById() {
        log.info("### getUserById START ###");
        ResultBean resultBean = null;
        try {
            resultBean = userService.getUserById();
        } catch (ApiValidateException e) {
            resultBean = new ResultBean(e.getCode(), e.getField(), e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            resultBean = new ResultBean(e.getMessage(), Constant.BAD_REQUEST);
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_REQUEST);
        }
        log.info("### getUserById END ###");
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
        log.info("### updateUser START ###");
        ResultBean resultBean = null;
        try {
            resultBean = userService.updateUser(json);
        } catch (ApiValidateException e) {
            resultBean = new ResultBean(e.getCode(), e.getField(), e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            resultBean = new ResultBean(e.getMessage(), Constant.BAD_REQUEST);
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_REQUEST);
        }
        log.info("### updateUser END ###");
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
        log.info("### linkToBank START ###");
        ResultBean resultBean = null;
        try {
            resultBean = userService.linkToBank(json);
        } catch (ApiValidateException e) {
            resultBean = new ResultBean(e.getCode(), e.getField(), e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            resultBean = new ResultBean(e.getMessage(), Constant.BAD_REQUEST);
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_REQUEST);
        }
        log.info("### linkToBank END ###");
        return new ResponseEntity<ResultBean>(resultBean, HttpStatus.OK);
    }

    /**
     * getBanksByUserId
     * @author: (VNEXT) ChinhTQ
     * @return
     */
    @RequestMapping(value = "/api/user/listbank", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> getBanksByUser() {
        log.info("### getBanksByUser START ###");
        List<UserBank> userBanks = null;
        ResultBean resultBean = null;
        try {
            userBanks = userService.getBanksByUser();
        } catch (ApiValidateException e) {
            resultBean = new ResultBean(e.getCode(), e.getField(), e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            resultBean = new ResultBean(e.getMessage(), Constant.BAD_REQUEST);
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_REQUEST);
        }
        resultBean = new ResultBean(userBanks, Constant.OK, "getBanksByUserId", properties.getProperty("ok"));
        log.info("### getBanksByUser END ###");
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
        log.info("### login START ###");
        JwtResponse jwtResponse = null;
        ResultBean resultBean = null;
        try {
            jwtResponse = userService.login(json);
        } catch (ApiValidateException e) {
            resultBean = new ResultBean(e.getCode(), e.getField(), e.getMessage());
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            resultBean = new ResultBean(HttpStatus.BAD_REQUEST, e.getMessage(), properties.getProperty("usernameLogin"));
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.BAD_REQUEST);
        }
        resultBean = new ResultBean(jwtResponse, Constant.OK, "login", properties.getProperty("ok"));
        log.info("### login END ###");
        return new ResponseEntity<ResultBean>(resultBean, HttpStatus.OK);
    }

}
