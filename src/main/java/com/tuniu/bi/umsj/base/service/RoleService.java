package com.tuniu.bi.umsj.base.service;

import com.tuniu.bi.umsj.base.exception.AbstractException;
import com.tuniu.bi.umsj.base.vo.RoleItem;
import com.tuniu.bi.umsj.base.vo.RoleListRequestVO;
import com.tuniu.bi.umsj.base.vo.RoleListResponseVO;

import java.util.List;

/**
 * @author zhangwei21
 */
public interface RoleService {

    /**
     * 查询所有
     * @return
     */
    List<RoleItem> findAll();

    /**
     * 分页查询
     * @param roleListRequestVO
     */
    RoleListResponseVO findMany(RoleListRequestVO roleListRequestVO);

    /**
     * 创建角色
     * @param roleItem
     * @return
     */
    int createRole(RoleItem roleItem) throws AbstractException;

    /**
     * 更新用户角色信息
     * @param roleItem
     */
    int updateRole(RoleItem roleItem) throws AbstractException;

    /**
     * 根据id删除角色
     * @param id
     */
    int deleteRole(Integer id) throws AbstractException;
}
