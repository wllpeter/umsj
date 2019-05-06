package com.tuniu.bi.umsj.base.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.tuniu.bi.umsj.base.dao.entity.OaUserEntity;
import com.tuniu.bi.umsj.base.dao.entity.OaUserParamEntity;
import com.tuniu.bi.umsj.base.dao.entity.UserEntity;
import com.tuniu.bi.umsj.base.dao.mapper.OaUserMapper;
import com.tuniu.bi.umsj.base.dao.mapper.UserMapper;
import com.tuniu.bi.umsj.base.exception.AbstractException;
import com.tuniu.bi.umsj.base.vo.UserListRequestVO;
import com.tuniu.bi.umsj.base.vo.UserListResponseVO;
import com.tuniu.bi.umsj.base.vo.UserUpdateReqeustVO;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
        return init(username, null);
    }

    @Override
    public UserEntity init(String username, String roleCodes) throws AbstractException {
        // 先查询用户是否在user表中存在
        UserEntity userEntity = userMapper.findByUsername(username);
        if (userEntity == null) {
            // 需要自动补充
            userEntity = oaClientService.getUser(username);
            if (userEntity != null) {
                // 则插入
                // 设置默认的角色
                userEntity.setRoleCodes(StringUtils.isEmpty(roleCodes) ? DEFAULT_ROLE_CODE : roleCodes);
                userMapper.insert(userEntity);
            }
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
        PageHelper.startPage(page, 20);
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

    @Override
    public UserListResponseVO findMany(UserListRequestVO requestVO) throws AbstractException {
        UserListResponseVO userListResponseVO = new UserListResponseVO();
        Integer page = requestVO.getPageNum();
        if (page == null) {
            page = 1;
        }
        Integer pageSize = requestVO.getPageSize();
        if (pageSize == null) {
            pageSize = 20;
        }
        String sortBy = StringUtils.isEmpty(requestVO.getSortBy()) ? "id" : requestVO.getSortBy();
        String order = StringUtils.isEmpty(requestVO.getOrder()) ? "DESC" : requestVO.getOrder();
        PageHelper.startPage(page, pageSize, sortBy + " " + order);
        List<UserEntity> many = userMapper.findMany(requestVO.getUsername());
        PageInfo<UserEntity> pageInfo = new PageInfo<>(many, pageSize);
        userListResponseVO.setPageNum(pageInfo.getPageNum());
        userListResponseVO.setPageSize(pageInfo.getPageSize());
        userListResponseVO.setTotal(pageInfo.getTotal());
        userListResponseVO.setPages(pageInfo.getPages());
        userListResponseVO.setUserList(many);
        return userListResponseVO;
    }

    @Override
    public int updateUser(UserUpdateReqeustVO userUpdateReqeustVO) {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        MapperFacade mapper = mapperFactory.getMapperFacade();
        UserEntity userEntity = mapper.map(userUpdateReqeustVO, UserEntity.class);
        return userMapper.update(userEntity);
    }
}
