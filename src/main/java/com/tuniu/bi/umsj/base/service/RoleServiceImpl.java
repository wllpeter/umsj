package com.tuniu.bi.umsj.base.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import com.tuniu.bi.umsj.base.dao.entity.RolesEntity;
import com.tuniu.bi.umsj.base.dao.entity.RolesParamEntity;
import com.tuniu.bi.umsj.base.dao.entity.UserEntity;
import com.tuniu.bi.umsj.base.dao.entity.UserParamEntity;
import com.tuniu.bi.umsj.base.dao.mapper.RolesMapper;
import com.tuniu.bi.umsj.base.dao.mapper.UserMapper;
import com.tuniu.bi.umsj.base.exception.AbstractException;
import com.tuniu.bi.umsj.base.exception.CommonException;
import com.tuniu.bi.umsj.base.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhangwei21
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RolesMapper rolesMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<RoleItem> findAll() {
        List<RolesEntity> many = rolesMapper.findAll();
        List<RoleItem> list = new ArrayList<>();
        for (RolesEntity rolesEntity : many) {
            RoleItem roleItem = new RoleItem();
            BeanUtils.copyProperties(rolesEntity, roleItem, "menus", "subMenus", "actions");
            list.add(roleItem);
        }
        return list;
    }

    @Override
    public RoleListResponseVO findMany(RoleListRequestVO roleListRequestVO) {

        RoleListResponseVO roleListResponseVO = new RoleListResponseVO();
        String orderCondition = roleListRequestVO.getSortBy() + " " + roleListRequestVO.getOrder();
        PageHelper.startPage(roleListRequestVO.getPageNum(), roleListRequestVO.getPageSize(), orderCondition);
        RolesParamEntity rolesParamEntity = new RolesParamEntity();
        rolesParamEntity.setCode(roleListRequestVO.getRoleCode());
        List<RolesEntity> list = rolesMapper.findMany(rolesParamEntity);
        PageInfo pageInfo = new PageInfo(list, roleListRequestVO.getPageSize());
        List<RoleItem> roleItems = new ArrayList<>();
        for (RolesEntity rolesEntity : list) {
            RoleItem roleItem = new RoleItem();
            BeanUtils.copyProperties(rolesEntity, roleItem, "menus", "subMenus", "actions");
            List<String> menus = new ArrayList<>();
            menus.addAll(Arrays.asList(rolesEntity.getMenus().split(",")));
            menus.addAll(Arrays.asList(rolesEntity.getSubmenus().split(",")));
            roleItem.setMenus(menus);
            roleItem.setActions(Arrays.asList(rolesEntity.getActions().split(",")));
            roleItems.add(roleItem);
        }
        roleListResponseVO.injectPageInfo(pageInfo);
        roleListResponseVO.setRoleList(roleItems);
        return roleListResponseVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "umsTransactionManager")
    public int createRole(CreateRoleReqeustVO reqeustVO) throws AbstractException {
        // 查询roleCode是否已经存在
        RolesParamEntity rolesParamEntity = new RolesParamEntity();
        rolesParamEntity.setCode(reqeustVO.getCode());
        List<RolesEntity> many = rolesMapper.findMany(rolesParamEntity);
        if (!CollectionUtils.isEmpty(many)) {
            throw new CommonException("该角色的code已存在，请重新输入!");
        }
        // 根据名称查找
        rolesParamEntity.setName(reqeustVO.getName());
        rolesParamEntity.setCode(null);
        many = rolesMapper.findMany(rolesParamEntity);
        if (!CollectionUtils.isEmpty(many)) {
            throw new CommonException("该角色的名称已存在，请重新输入!");
        }
        RolesEntity rolesEntity = new RolesEntity();
        BeanUtils.copyProperties(reqeustVO, rolesEntity, "menus", "subMenus", "actions");
        rolesEntity.setMenus(Joiner.on(",").join(reqeustVO.getMenus()));
        rolesEntity.setSubmenus(Joiner.on(",").join(reqeustVO.getSubMenus()));
        rolesEntity.setActions(Joiner.on(",").join(reqeustVO.getActions()));
        return rolesMapper.insert(rolesEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "umsTransactionManager")
    public int updateRole(UpdateRoleRequestVO requestVO) throws AbstractException {
        // 根据id查询
        RolesEntity one = rolesMapper.findByPk(requestVO.getId());
        if (one == null) {
            throw new CommonException("根据id[" + requestVO.getId() + "]查询不到角色信息");
        }
        if (!one.getName().equals(requestVO.getName())) {
            // 表示名称变更
            RolesParamEntity rolesParamEntity = new RolesParamEntity();
            rolesParamEntity.setName(requestVO.getName());
            List<RolesEntity> many = rolesMapper.findMany(rolesParamEntity);
            if (!CollectionUtils.isEmpty(many)) {
                throw new CommonException("该角色的名称已存在，请重新输入!");
            }
        }
        one.setName(requestVO.getName());
        one.setMenus(Joiner.on(",").join(requestVO.getMenus()));
        one.setSubmenus(Joiner.on(",").join(requestVO.getSubMenus()));
        one.setActions(Joiner.on(",").join(requestVO.getActions()));
        return rolesMapper.update(one);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "umsTransactionManager")
    public int deleteRole(Integer id) throws AbstractException {
        //  查询改角色有没有被占用
        RolesEntity byPk = rolesMapper.findByPk(id);
        if (byPk == null) {
            throw new CommonException("该角色不存在，无法删除");
        }
        // 查询用户列表是否有包含该角色的用户
        UserParamEntity userParamEntity = new UserParamEntity();
        userParamEntity.setRoleCode(byPk.getCode());
        List<UserEntity> list = userMapper.findByParams(userParamEntity);
        if (!CollectionUtils.isEmpty(list)) {
            throw new CommonException("该角色已经被关联，无法删除");
        }
        return rolesMapper.deleteByPk(id);
    }
}
