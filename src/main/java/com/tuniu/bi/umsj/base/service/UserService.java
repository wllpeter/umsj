package com.tuniu.bi.umsj.base.service;

import com.tuniu.bi.umsj.base.dao.entity.UserEntity;
import com.tuniu.bi.umsj.base.exception.AbstractException;

import java.util.List;

/**
 * 用户服务类
 *
 * @author zhangwei21
 */
public interface UserService {

    /**
     * 根据主键查询用户信息
     * @param id
     * @return
     */
    UserEntity findById(Integer id);

    /**
     * 初始化用户
     * @param username
     * @throws AbstractException
     * @return UserEntity
     */
    UserEntity init(String username) throws AbstractException;


    /**
     * 获取接收者信息
     * @param type
     * @param names
     * @return
     */
    List obtainReceiver(Integer type, List<String> names);

    /**
     * 同步oaUser的信息
     */
    void  syncOaUserInfo();

    /**
     * 补充用户信息
     * @param usernames
     * @throws AbstractException
     */
    void supplyUserInfo(List<String> usernames) throws AbstractException;
}
