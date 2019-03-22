package com.tuniu.bi.umsj.exception;

import com.tuniu.bi.umsj.mapper.ExceptionLogMapper;
import com.tuniu.bi.umsj.mapper.entity.ExceptionLogEntity;

/**
 * 异常日志记录
 *
 * @author zhangwei
 */
public class ExceptionLogTask implements Runnable {

    private ExceptionLogMapper exceptionLogMapper;

    private ExceptionLogEntity exceptionLogEntity;

    public ExceptionLogTask(ExceptionLogMapper exceptionLogMapper, ExceptionLogEntity exceptionLogEntity) {
        this.exceptionLogMapper = exceptionLogMapper;
        this.exceptionLogEntity = exceptionLogEntity;
    }

    @Override
    public void run() {
        exceptionLogMapper.insert(exceptionLogEntity);
    }
}
