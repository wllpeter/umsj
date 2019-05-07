package com.tuniu.bi.umsj.base.dao.mapper;

import com.tuniu.bi.umsj.base.annotation.UmsMapper;
import com.tuniu.bi.umsj.base.dao.entity.RolesEntity;
import com.tuniu.bi.umsj.base.dao.entity.RolesParamEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhangwei21
 */
@UmsMapper
public interface RolesMapper {

    /**
     * 查询多个
     *
     * @param rolesParamEntity
     * @return
     */
    List<RolesEntity> findMany(RolesParamEntity rolesParamEntity);


    /**
     * 查询所有角色
     *
     * @return
     */
    List<RolesEntity> findAll();

    /**
     * 创建角色
     *
     * @param rolesEntity
     * @return
     */
    int insert(RolesEntity rolesEntity);

    /**
     * 根据主键查找
     *
     * @param id
     * @return
     */
    RolesEntity findByPk(@Param("id") Integer id);

    /**
     * 更新角色信息
     *
     * @param rolesEntity
     * @return
     */
    int update(RolesEntity rolesEntity);

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    int deleteByPk(@Param("id") Integer id);
}
