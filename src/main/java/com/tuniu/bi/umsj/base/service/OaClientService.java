package com.tuniu.bi.umsj.base.service;

import com.tuniu.bi.umsj.base.dao.entity.UserEntity;
import com.tuniu.bi.umsj.base.exception.AbstractException;

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
