package com.tuniu.bi.umsj.mapper;

import com.tuniu.bi.umsj.mapper.entity.ExceptionLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 异常日志的mapper类
 *
 * @author zhangwei21
 */
@Mapper
public interface ExceptionLogMapper {

    /**
     * 插入异常日志
     * @param exceptionLogEntity
     * @return
     */
    int insert(ExceptionLogEntity exceptionLogEntity);
}
