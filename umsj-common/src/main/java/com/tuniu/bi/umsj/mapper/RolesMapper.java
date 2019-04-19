package com.tuniu.bi.umsj.mapper;

import com.tuniu.bi.umsj.mapper.entity.RolesEntity;
import com.tuniu.bi.umsj.mapper.entity.RolesParamEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zhangwei21
 */
@Mapper
public interface RolesMapper {

    /**
     * 查询多个
     * @param rolesParamEntity
     * @return
     */
    List<RolesEntity> findMany(RolesParamEntity rolesParamEntity);
}
