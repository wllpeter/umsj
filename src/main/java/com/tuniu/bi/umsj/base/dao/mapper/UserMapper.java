package com.tuniu.bi.umsj.base.dao.mapper;

import com.tuniu.bi.umsj.base.annotation.UmsMapper;
import com.tuniu.bi.umsj.base.dao.entity.UserEntity;
import com.tuniu.bi.umsj.base.dao.entity.UserParamEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户数据库操作类
 * @author zhangwei21
 */
@UmsMapper
public interface UserMapper {

    /**
     * 根据主键查询用户信息
     * @param id
     * @return
     */
    UserEntity findByPk(@Param("id") Integer id);


    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    UserEntity findByUsername(@Param("username") String username);

    /**
     * 插入用户信息
     * @param userEntity
     * @return
     */
    int insert(UserEntity userEntity);

    /**
     * 更新用户信息
     * @param userEntity
     * @return
     */
    int update(UserEntity userEntity);

    /**
     * 查找所有
     * @return
     */
    List<UserEntity> findAll();

    /**
     * 根据名称查找
     * @return
     */
    List<UserEntity> findMany(@Param("username") String username);

    /**
     * 根据特定的参数查找
     * @param userParamEntity
     * @return
     */
    List<UserEntity> findByParams(UserParamEntity userParamEntity);
}
