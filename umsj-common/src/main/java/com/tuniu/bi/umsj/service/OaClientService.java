package com.tuniu.bi.umsj.service;

import com.tuniu.bi.umsj.exception.AbstractException;

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
}
