package com.tuniu.bi.umsj.base.dao.mapper;

import com.tuniu.bi.umsj.base.annotation.UmsjMapper;
import com.tuniu.bi.umsj.base.dao.entity.ExceptionLogEntity;

/**
 * 异常日志的mapper类
 *
 * @author zhangwei21
 */
@UmsjMapper
public interface ExceptionLogMapper {

    /**
     * 插入异常日志
     * @param exceptionLogEntity
     * @return
     */
    int insert(ExceptionLogEntity exceptionLogEntity);
}
