package com.tuniu.bi.umsj.base.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.tuniu.bi.umsj.base.dao.entity.*;
import com.tuniu.bi.umsj.base.dao.mapper.OaUserMapper;
import com.tuniu.bi.umsj.base.dao.mapper.RolesMapper;
import com.tuniu.bi.umsj.base.dao.mapper.UserMapper;
import com.tuniu.bi.umsj.base.exception.AbstractException;
import com.tuniu.bi.umsj.base.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private RolesMapper rolesMapper;

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
    @Transactional(rollbackFor = Exception.class, transactionManager = "umsTransactionManager")
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
        PageHelper.startPage(requestVO.getPageNum(), requestVO.getPageSize(), requestVO.getSortBy() + " " + requestVO.getOrder());
        List<UserEntity> many = userMapper.findMany(requestVO.getUsername());
        List<UserItem> userItemList = new ArrayList<>();
        RolesParamEntity rolesParamEntity = new RolesParamEntity();
        for (UserEntity userEntity : many) {
            UserItem userItem = new UserItem();
            BeanUtils.copyProperties(userEntity, userItem);
            // 查询roleCodes的名称
            String roleCodes = userEntity.getRoleCodes();
            List<String> codes = Arrays.asList(roleCodes.split(","));
            rolesParamEntity.setCodes(codes);
            List<RolesEntity> rolesList = rolesMapper.findMany(rolesParamEntity);
            List<RoleItem> roleItemList = new ArrayList<>();
            for (RolesEntity rolesEntity : rolesList) {
                RoleItem roleItem = new RoleItem();
                BeanUtils.copyProperties(rolesEntity, roleItem);
                roleItemList.add(roleItem);
            }
            userItem.setRoleItems(roleItemList);
            userItemList.add(userItem);
        }
        PageInfo<UserEntity> pageInfo = new PageInfo<>(many, requestVO.getPageSize());
        userListResponseVO.injectPageInfo(pageInfo);
        userListResponseVO.setUserList(userItemList);
        return userListResponseVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "umsTransactionManager")
    public int updateUser(UserUpdateRequestVO userUpdateReqeustVO) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userUpdateReqeustVO, userEntity, "roleCodes");
        String roleCodes = Joiner.on(",").join(userUpdateReqeustVO.getRoleCodes());
        userEntity.setRoleCodes(roleCodes);
        return userMapper.update(userEntity);
    }

    @Override
    public UserInfoResponseVO getUserInfo(String username) {
        UserInfoResponseVO responseVO = new UserInfoResponseVO();
        UserEntity userEntity = userMapper.findByUsername(username);
        String roleCodes = userEntity.getRoleCodes();
        List<String> roleList = Arrays.asList(roleCodes.split(","));
        RolesParamEntity rolesParamEntity = new RolesParamEntity();
        rolesParamEntity.setCodes(roleList);
        List<RolesEntity> many = rolesMapper.findMany(rolesParamEntity);
        List<String> menus = new ArrayList<>();
        List<String> actions = new ArrayList<>();
        for (RolesEntity rolesEntity : many) {
            List<String> tmpMenus = Splitter.on(",").trimResults().splitToList(rolesEntity.getMenus());
            List<String> tmpSubMenus = Splitter.on(",").trimResults().splitToList(rolesEntity.getSubmenus());
            menus.addAll(tmpMenus);
            menus.addAll(tmpSubMenus);
            List<String> tmpActions = Splitter.on(",").trimResults().splitToList(rolesEntity.getActions());
            actions.addAll(tmpActions);
        }
        List<String> finalMenus = menus.stream().distinct().collect(Collectors.toList());
        List<String> finalActions = actions.stream().distinct().collect(Collectors.toList());
        BeanUtils.copyProperties(userEntity, responseVO);
        responseVO.setActions(finalActions);
        responseVO.setMenus(finalMenus);
        responseVO.setRoles(roleList);
        return responseVO;
    }
}
