package com.tuniu.bi.umsj.mapper;

import com.tuniu.bi.umsj.annotation.UmsjMapper;
import com.tuniu.bi.umsj.mapper.entity.OaUserEntity;
import com.tuniu.bi.umsj.mapper.entity.OaUserParamEntity;
import org.apache.ibatis.annotations.Mapper;

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
