/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////
package com.example.demo.helper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.bean.entity.UserEntity;
import com.example.demo.service.UserService;

/**
 * [OVERVIEW] XXXXX.
 *
 * @author: (VNEXT) ChinhTQ
 * @version: 1.0
 * @History
 * [NUMBER]  [VER]     [DATE]          [USER]             [CONTENT]
 * --------------------------------------------------------------------------
 * 001       1.0       2020/04/17      (VNEXT) ChinhTQ       Create new
*/
@Service
public class AuthenticationHelper {
    private static final Log log = LogFactory.getLog(AuthenticationHelper.class);
    @Autowired
    private UserService userService;

    /**
     * getLoggedInUser
     * @author: (VNEXT) ChinhTQ
     * @return
     */
    public UserEntity getLoggedInUser() {
        log.debug("### getLoggedInUser START ###");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntity userEntity = userService.getByUsername(userName);
        log.debug("### getLoggedInUser END ###");
        return userEntity;
    }
}
