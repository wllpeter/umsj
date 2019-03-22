package com.tuniu.bi.umsj.service;

import com.tuniu.bi.umsj.exception.AbstractException;
import com.tuniu.bi.umsj.mapper.entity.UserEntity;

/**
 * oa相关服务类
 *
 * @author zhangwei21
 */
public interface OaClientService {

    /**
     * 检查账号(验证用户名密码)
     */
    void checkOaAccount(String username, String password) throws AbstractException;


    /**
     * 获取用户信息
     */
    UserEntity getUser(String username) throws AbstractException;
}
