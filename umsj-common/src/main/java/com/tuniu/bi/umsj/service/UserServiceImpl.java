package com.tuniu.bi.umsj.service;

import com.github.pagehelper.PageHelper;
import com.google.common.base.Strings;
import com.tuniu.bi.umsj.exception.AbstractException;
import com.tuniu.bi.umsj.mapper.OaUserMapper;
import com.tuniu.bi.umsj.mapper.UserMapper;
import com.tuniu.bi.umsj.mapper.entity.OaUserEntity;
import com.tuniu.bi.umsj.mapper.entity.OaUserParamEntity;
import com.tuniu.bi.umsj.mapper.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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

    @Autowired
    private OaUserMapper oaUserMapper;

    /**
     * 邮箱后缀
     */
    private static final String EMAIL_SUFFIX = "@tuniu.com";

    /**
     * 初始化用户默认的角色
     */
    private static final String DEFAULT_ROLE_CODE = "ROLE_DEFAULT";

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
            // 设置默认的角色
            userEntity.setRoleCodes(DEFAULT_ROLE_CODE);
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
                ret.add(name + EMAIL_SUFFIX);
            } else if (type == 3) {
                String phone = user.getPhone();
                if (!Strings.isNullOrEmpty(phone)) {
                    ret.add(phone);
                }
            } else {
                LOGGER.error("暂不支持的类型");
            }
        }
        return ret;
    }

    /**
     * 同步OaUser的信息
     */
    @Override
    public void syncOaUserInfo() {
        int page = 1;
        PageHelper.startPage(page, 5);
        List<UserEntity> all = userMapper.findAll();
        OaUserParamEntity oaUserParamEntity = new OaUserParamEntity();
        // 查询在职
        oaUserParamEntity.setIsLeave(0);
        while (!CollectionUtils.isEmpty(all)) {
            for (UserEntity userEntity : all) {
                oaUserParamEntity.setUsername(userEntity.getUsername());
                OaUserEntity one = oaUserMapper.findOne(oaUserParamEntity);
                if (one != null) {
                    userEntity.setSalerId(one.getSalerId());
                    userEntity.setPhone(one.getCellphone());
                    userMapper.update(userEntity);
                }
            }
            page++;
            PageHelper.startPage(page, 5);
            all = userMapper.findAll();
        }
    }


    @Override
    public void supplyUserInfo(List<String> usernames) throws AbstractException {
        UserEntity sourceUser;
        UserEntity user;
        for (String name : usernames) {
            sourceUser = userMapper.findByUsername(name);
            if (sourceUser != null) {
                user = oaClientService.getUser(name);
                if (user != null) {
                    sourceUser.setSalerId(user.getSalerId());
                    sourceUser.setPhone(user.getPhone());
                    userMapper.update(sourceUser);
                }
            }
        }
    }
}
