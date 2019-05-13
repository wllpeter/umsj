package com.tuniu.bi.umsj.uds.dao.mapper;

import com.tuniu.bi.umsj.base.annotation.UmsMapper;
import com.tuniu.bi.umsj.uds.dao.entity.UdsPublishLogEntity;

/**
 * @author zhangwei21
 */
@UmsMapper
public interface UdsPublishLogMapper {

    /**
     * 插入
     * @param udsPublishLogEntity
     * @return
     */
    int insert(UdsPublishLogEntity udsPublishLogEntity);
}
