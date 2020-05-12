/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package com.example.demo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.bean.entity.UserEntity;

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
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    /**
     * findByUsername
     * @author: (VNEXT) ChinhTQ
     * @param username
     * @return
     */
    Optional<UserEntity> findByUsername(String username);
}
