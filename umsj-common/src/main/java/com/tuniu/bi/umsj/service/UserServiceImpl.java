package com.tuniu.bi.umsj.service;

import com.tuniu.bi.umsj.exception.AbstractException;
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

    @Autowired
    private OaClientService oaClientService;

    @Override
    public UserEntity findById(Integer id) {
        UserEntity byBk = userMapper.findByPk(id);
        return byBk;
    }

    @Override
    public UserEntity init(String username) throws AbstractException {
        // 先查询用户是否在user表中存在
        UserEntity userEntity = userMapper.findByUsername(username);
        if (userEntity == null) {
            // 需要自动补充
            userEntity = oaClientService.getUser(username);

        }
        if (userEntity != null) {
            // 则插入
            userMapper.insert(userEntity);
        }
        return userEntity;
    }
}
