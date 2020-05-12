/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package com.example.demo.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.bean.ResultBean;
import com.example.demo.bean.entity.RoleEntity;
import com.example.demo.bean.entity.UserBankEntity;
import com.example.demo.bean.entity.UserEntity;
import com.example.demo.bean.model.JwtResponse;
import com.example.demo.bean.model.UserBank;
import com.example.demo.bean.model.UserDto;
import com.example.demo.dao.UserDao;
import com.example.demo.dao.UserRepository;
import com.example.demo.helper.AuthenticationHelper;
import com.example.demo.service.BankService;
import com.example.demo.service.UserService;
import com.example.demo.ultil.ApiValidateException;
import com.example.demo.ultil.Constant;
import com.example.demo.ultil.JwtTokenUtil;
import com.example.demo.ultil.ReadProperties;
import com.example.demo.ultil.Validate;

/**
 * 
 * [OVERVIEW] XXXXX.
 *
 * @author: ChinhTQ
 * @version: 1.0
 * @History
 * [NUMBER]  [VER]     [DATE]          [USER]             [CONTENT]
 * --------------------------------------------------------------------------
 * 001       1.0       2020/04/14      ChinhTQ       Create new
 */

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthenticationHelper authenticationHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private BankService bankService;

    @Autowired
    private UserRepository userRepository;
    private Validate validate;
    @SuppressWarnings("static-access")
    private Properties properties = new ReadProperties().readProperties();
    static final Constant constant = new Constant();

    /**
     * createUser
     * @author: (VNEXT) ChinhTQ
     * @param json
     * @return UserEntity
     * @throws ApiValidateException
     */
    @SuppressWarnings("static-access")
    @Override
    public ResultBean createUser(String json) throws ApiValidateException {
        log.debug("### createUser START ###");
        JSONObject jsonObject = new JSONObject(json);
        UserEntity user = new UserEntity();
        // check jsonObject
        if (jsonObject.isEmpty()) {
            throw new ApiValidateException(Constant.METHOD_NOT_ALLOWED, "create", properties.getProperty("inputFaild"));
        }

        // check userName
        if (jsonObject.has("username")) {
            String username = jsonObject.getString("username");
            boolean checkUserName = username.matches(validate.USERNAME);
            if (checkUserName == false) {
                throw new ApiValidateException(Constant.METHOD_NOT_ALLOWED, "create user", properties.getProperty("username"));
            } else {
                user.setUsername(username);
            }
        }

        // check password
        if (jsonObject.has("password")) {
            String password = jsonObject.getString("password");
            boolean checkPassWord = password.matches(validate.PASSWORD);
            if (checkPassWord == false) {
                throw new ApiValidateException(Constant.METHOD_NOT_ALLOWED, "create user", properties.getProperty("password"));
            } else {
                user.setPassword(bCryptPasswordEncoder.encode(password));
            }
        }

        // check numberPhone
        if (jsonObject.has("phoneNumber")) {
            String phoneNumber = jsonObject.getString("phoneNumber");
            boolean checkphoneNumber = phoneNumber.matches(validate.NUMBER_PHONE);
            if (checkphoneNumber == false) {
                throw new ApiValidateException(Constant.METHOD_NOT_ALLOWED, "create user", properties.getProperty("numberPhone"));
            } else {
                user.setPhoneNumber(phoneNumber);
            }
        }

        // check email
        if (jsonObject.has("email")) {
            String email = jsonObject.getString("email");
            boolean checkEmail = email.matches(validate.EMAIL);
            if (checkEmail == false) {
                throw new ApiValidateException(Constant.METHOD_NOT_ALLOWED, "create user", properties.getProperty("email"));
            } else {
                user.setEmail(email);
            }
        }

        // check date of birth
        if (jsonObject.has("dOBirth")) {
            String dOBirth = jsonObject.getString("dOBirth");
            boolean checkdOBirth = dOBirth.matches(validate.DATE);
            if (checkdOBirth == false) {
                throw new ApiValidateException(Constant.METHOD_NOT_ALLOWED, "create user", properties.getProperty("dOBirth"));
            } else {
                user.setdOBirth(dOBirth);
            }
        }

        RoleEntity role = new RoleEntity();
        role.setRoleId(2);
        role.setRoleName("MEMBER");
        Set<RoleEntity> roles = new HashSet<>();
        roles.add(role);
        user.setRole(roles);
        user.setBankId(jsonObject.getInt("bankId"));
        userDao.createUser(user);
        UserDto userDto = userDao.getUserDtoByUserId(user.getUserId());
        ResultBean resultBean = new ResultBean(userDto, constant.OK, properties.getProperty("ok"));
        log.debug("### createUser END ###");
        return resultBean;
    }

    /**
     * getUserById
     * @author: (VNEXT) ChinhTQ
     * @param id
     * @return UserEntity
     * @throws ApiValidateException
     */
    @Override
    public ResultBean getUserById() throws ApiValidateException {
        log.debug("### getUserById START ###");
        UserEntity user = authenticationHelper.getLoggedInUser();
        if (user == null) {
            throw new ApiValidateException(Constant.BAD_GATEWAY, "getUser", properties.getProperty("unauthorize"));
        }
        UserDto userDto = userDao.getUserDtoByUserId(user.getUserId());
        ResultBean resultBean = new ResultBean(userDto, "getUser", properties.getProperty("ok"));
        log.debug("### getUserById END ###");
        return resultBean;
    }

    /**
     * updateUserById
     * @author: (VNEXT) ChinhTQ
     * @param json
     * @return UserEntity
     * @throws ApiValidateException
     */
    @SuppressWarnings("static-access")
    @Override
    public ResultBean updateUser(String json) throws ApiValidateException {
        log.debug("### updateUserById START ###");
        UserEntity user = authenticationHelper.getLoggedInUser();
        JSONObject jsonObject = new JSONObject(json);
        if (jsonObject.isEmpty()) {
            throw new ApiValidateException(Constant.METHOD_NOT_ALLOWED, "update", properties.getProperty("inputFaild"));
        }

        // check userName
        if (jsonObject.has("username")) {
            String username = jsonObject.getString("username");
            boolean checkUserName = username.matches(validate.USERNAME);
            if (checkUserName == false) {
                throw new ApiValidateException(Constant.METHOD_NOT_ALLOWED, "update user", properties.getProperty("username"));
            } else {
                user.setUsername(username);
            }
        }

        // check password
        if (jsonObject.has("password")) {
            String password = jsonObject.getString("password");
            boolean checkPassWord = password.matches(validate.PASSWORD);
            if (checkPassWord == false) {
                throw new ApiValidateException(Constant.METHOD_NOT_ALLOWED, "update user", properties.getProperty("password"));
            } else {
                user.setPassword(bCryptPasswordEncoder.encode(password));
            }
        }

        // check numberPhone
        if (jsonObject.has("phoneNumber")) {
            String phoneNumber = jsonObject.getString("phoneNumber");
            boolean checkphoneNumber = phoneNumber.matches(validate.NUMBER_PHONE);
            if (checkphoneNumber == false) {
                throw new ApiValidateException(Constant.METHOD_NOT_ALLOWED, "update user", properties.getProperty("numberPhone"));
            } else {
                user.setPhoneNumber(phoneNumber);
            }
        }

        // check email
        if (jsonObject.has("email")) {
            String email = jsonObject.getString("email");
            boolean checkEmail = email.matches(validate.EMAIL);
            if (checkEmail == false) {
                throw new ApiValidateException(Constant.METHOD_NOT_ALLOWED, "update user", properties.getProperty("email"));
            } else {
                user.setEmail(email);
            }
        }

        // check date of birth
        if (jsonObject.has("dOBirth")) {
            String dOBirth = jsonObject.getString("dOBirth");
            boolean checkdOBirth = dOBirth.matches(validate.DATE);
            if (checkdOBirth == false) {
                throw new ApiValidateException(Constant.METHOD_NOT_ALLOWED, "update user", properties.getProperty("dOBirth"));
            } else {
                user.setdOBirth(dOBirth);
            }
        }

        userDao.updateUser(user);
        ResultBean resultBean = new ResultBean(user, constant.OK, properties.getProperty("OK"));
        log.debug("### updateUserById END ###");
        return resultBean;
    }

    /**
     * linkToBank
     * @author: (VNEXT) ChinhTQ
     * @param id
     * @param json
     * @return UserBankEntity
     * @throws ApiValidateException
     */
    @Override
    public ResultBean linkToBank(String json) throws ApiValidateException {
        log.debug("### linkToBank START ###");
        UserEntity user = authenticationHelper.getLoggedInUser();
        List<UserBankEntity> userBankEntities = this.getAllBankByUser();
        JSONObject jsonObject = new JSONObject(json);
        Integer bankId = jsonObject.getInt("bankId");

        if (bankId.equals(user.getBankId())) {
            throw new ApiValidateException(Constant.METHOD_NOT_ALLOWED, "link to bank", "Ngân hàng liên kết đã được đăng kí");
        }
        for (UserBankEntity ub : userBankEntities) {
            if (bankId.equals(ub.getBankId())) {
                throw new ApiValidateException(Constant.METHOD_NOT_ALLOWED, "link to bank", "Ngân hàng đã được liên kết");
            }
        }
        UserBankEntity userBankEntity = new UserBankEntity();
        userBankEntity.setUserId(user.getUserId());
        userBankEntity.setBankId(bankId);
        userDao.linkToBank(userBankEntity);
        log.debug("### linkToBank END ###");
        return bankService.getBankById(bankId);
    }

    /**
     * getAllBankByUserId
     * @author: (VNEXT) ChinhTQ
     * @param id
     * @return List<UserBankEntity> of one user
     * @throws ApiValidateException 
     */
    @Override
    public List<UserBankEntity> getAllBankByUser() throws ApiValidateException {
        log.debug("### getAllBankByUserId START ###");
        UserEntity user = authenticationHelper.getLoggedInUser();
        List<UserBankEntity> userBankEntities = userDao.getAllBankByUserId(user.getUserId());
        log.debug("### getAllBankByUserId END ###");
        return userBankEntities;
    }

    /**
     * getBanksByUserId
     * @author: (VNEXT) ChinhTQ
     * @return List<UserBank> of one user
     * @throws ApiValidateException 
     */
    @Override
    public List<UserBank> getBanksByUser() throws ApiValidateException {
        log.debug("### getBanksByUserId START ###");
        UserEntity user = authenticationHelper.getLoggedInUser();
        List<UserBank> userBanks = userDao.getBanksByUserId(user.getUserId());
        if (userBanks.isEmpty()) {
            throw new ApiValidateException(Constant.METHOD_NOT_ALLOWED, "getBanksByUserId", "Bạn chưa đăng kí liên kết");
        }
        log.debug("### getBanksByUserId END ###");
        return userBanks;
    }

    /**
     * login
     * @author: (VNEXT) ChinhTQ
     * @param json
     * @return JwtResponse
     * @throws ApiValidateException
     * @throws Exception
     */
    @SuppressWarnings("static-access")
    @Override
    public JwtResponse login(String json) throws ApiValidateException, Exception {
        log.debug("### login START ###");
        JSONObject jsonObject = new JSONObject(json);
        if (jsonObject.isEmpty()) {
            throw new ApiValidateException(Constant.NOT_FOUND, "faild", "not found");
        }
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        JwtResponse jwtResponse = null;
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (username.equalsIgnoreCase(userDetails.getUsername()) && bCryptPasswordEncoder.matches(password, userDetails.getPassword())) {
            authenticate(username, password);
            String token = jwtTokenUtil.generateToken(userDetails);
            UserEntity user = this.getByUsername(username);
            UserDto userDto = userDao.getUserDtoByUserId(user.getUserId());
            jwtResponse = new JwtResponse(token, userDto);
        } else {
            throw new ApiValidateException(Constant.FORBIDDEN, "login", properties.getProperty("loginFaild"));
        }

        log.debug("### login END ###");
        return jwtResponse;
    }

    /**
     * authenticate
     * @author: (VNEXT) ChinhTQ
     * @param username
     * @param password
     * @throws Exception
     */
    @Override
    public void authenticate(String username, String password) throws Exception {
        log.debug("### authenticate START ###");
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
        log.debug("### authenticate END ###");
    }

    /**
     * getByUsername
     * @author: (VNEXT) ChinhTQ
     * @param username
     * @return UserEntity
     * @throws ApiValidateException 
     */
    @SuppressWarnings("static-access")
    @Override
    public UserEntity getByUsername(String username) throws ApiValidateException {
        log.debug("### getByUsername START ###");

        Optional<UserEntity> userOptional = userRepository.findByUsername(username);
        if (userOptional == null) {
            throw new ApiValidateException(constant.BAD_REQUEST, "login", properties.getProperty("inputLoginNull"));
        }

        log.debug("### getByUsername END ###");
        return userOptional.orElse(null);
    }
}
