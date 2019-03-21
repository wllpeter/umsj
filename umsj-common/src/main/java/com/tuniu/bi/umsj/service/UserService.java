package com.tuniu.bi.umsj.service;

import com.tuniu.bi.umsj.mapper.entity.UserEntity;

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
}
