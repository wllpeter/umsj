package com.tuniu.bi.umsj.service;

import com.tuniu.bi.umsj.mapper.UserMapper;
import com.tuniu.bi.umsj.mapper.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangwei21
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserEntity findById(Integer id) {
        UserEntity byBk = userMapper.findByPk(id);
        return byBk;
    }
}
