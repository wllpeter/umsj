package com.tuniu.bi.umsj.service;

import com.google.common.base.Strings;
import com.tuniu.bi.umsj.exception.AbstractException;
import com.tuniu.bi.umsj.mapper.UserMapper;
import com.tuniu.bi.umsj.mapper.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangwei21
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OaClientService oaClientService;

    /**
     * 邮箱后缀
     */
    private static final String EMAIL_SUFFIX = "@tuniu.com";

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

    @Override
    public List<?> obtainReceiver(Integer type, List<String> names) {
        // 查询user表,查询salerId，phone
        List<Object> ret = new ArrayList<>();
        for (String name : names) {
            UserEntity user = userMapper.findByUsername(name);
            if (user == null) {
                continue;
            }
            if (type == 1) {
                Integer salerId = user.getSalerId();
                if (salerId != null && salerId != 0) {
                    ret.add(salerId);
                }
            } else if (type == 2) {
                String phone = user.getPhone();
                if (!Strings.isNullOrEmpty(phone)) {
                    ret.add(phone);
                }
            } else if (type == 3) {
                ret.add(name + EMAIL_SUFFIX);
            } else {
                LOGGER.error("暂不支持的类型");
            }
        }
        return ret;
    }
}
