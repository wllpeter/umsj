package com.tuniu.bi.umsj.base.service;

import com.tuniu.bi.umsj.base.vo.RoleItem;

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
}
