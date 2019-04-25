package com.tuniu.bi.umsj.base.dao.mapper;

import com.tuniu.bi.umsj.base.annotation.UmsjMapper;
import com.tuniu.bi.umsj.base.dao.entity.OaUserEntity;
import com.tuniu.bi.umsj.base.dao.entity.OaUserParamEntity;

/**
 * @author zhangwei21
 */
@UmsjMapper
public interface OaUserMapper {
    /**
     * 查找单个
     * @param oaUserParamEntity
     * @return
     */
    OaUserEntity findOne(OaUserParamEntity oaUserParamEntity);
}
