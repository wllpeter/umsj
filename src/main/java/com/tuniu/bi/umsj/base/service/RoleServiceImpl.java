package com.tuniu.bi.umsj.base.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tuniu.bi.umsj.base.dao.entity.RolesEntity;
import com.tuniu.bi.umsj.base.dao.entity.RolesParamEntity;
import com.tuniu.bi.umsj.base.dao.mapper.RolesMapper;
import com.tuniu.bi.umsj.base.utils.BeanMapper;
import com.tuniu.bi.umsj.base.vo.RoleItem;
import com.tuniu.bi.umsj.base.vo.RoleListRequestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangwei21
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RolesMapper rolesMapper;

    @Override
    public List<RoleItem> findAll() {
        List<RolesEntity> many = rolesMapper.findAll();
        return BeanMapper.mapList(many, RolesEntity.class, RoleItem.class);
    }

    @Override
    public List<RoleItem> findMany(RoleListRequestVO roleListRequestVO) {

        String orderCondition = roleListRequestVO.getSortBy() + " " + roleListRequestVO.getOrder();
        PageHelper.startPage(roleListRequestVO.getPageNum(), roleListRequestVO.getPageSize(), orderCondition);
        RolesParamEntity rolesParamEntity = new RolesParamEntity();
        rolesParamEntity.setCode(roleListRequestVO.getRoleCode());
        List<RolesEntity> list = rolesMapper.findMany(rolesParamEntity);
        PageInfo pageInfo = new PageInfo(list, roleListRequestVO.getPageSize());




        return null;
    }

}
