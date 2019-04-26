package com.tuniu.bi.umsj.base.dao.mapper;

import com.tuniu.bi.umsj.base.annotation.UmsMapper;
import com.tuniu.bi.umsj.base.dao.entity.ExceptionLogEntity;

/**
 * 异常日志的mapper类
 *
 * @author zhangwei21
 */
@UmsMapper
public interface ExceptionLogMapper {

    /**
     * 插入异常日志
     * @param exceptionLogEntity
     * @return
     */
    int insert(ExceptionLogEntity exceptionLogEntity);
}
