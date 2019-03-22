package com.tuniu.bi.umsj.mapper;

import com.tuniu.bi.umsj.mapper.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户数据库操作类
 * @author zhangwei21
 */
@Mapper
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
}
