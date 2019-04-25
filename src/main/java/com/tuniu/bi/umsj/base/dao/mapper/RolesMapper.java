package com.tuniu.bi.umsj.base.dao.mapper;

import com.tuniu.bi.umsj.base.annotation.UmsjMapper;
import com.tuniu.bi.umsj.base.dao.entity.RolesEntity;
import com.tuniu.bi.umsj.base.dao.entity.RolesParamEntity;

import java.util.List;

/**
 * @author zhangwei21
 */
@UmsjMapper
public interface RolesMapper {

    /**
     * 查询多个
     * @param rolesParamEntity
     * @return
     */
    List<RolesEntity> findMany(RolesParamEntity rolesParamEntity);
}
