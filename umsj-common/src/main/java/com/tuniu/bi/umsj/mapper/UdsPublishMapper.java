package com.tuniu.bi.umsj.mapper;

import com.tuniu.bi.umsj.mapper.entity.UdsPublishEntity;
import com.tuniu.bi.umsj.mapper.entity.UdsPublishParamEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zhangwei21
 */
@Mapper
public interface UdsPublishMapper {

    /**
     * 查询多个
     * @param udsPublishParamEntity
     * @return
     */
    List<UdsPublishEntity> findMany(UdsPublishParamEntity udsPublishParamEntity);

    /**
     * 插入
     * @param udsPublishEntity
     * @return
     */
    int insert(UdsPublishEntity udsPublishEntity);

    /**
     * 更新
     * @param UdsPublishEntity
     * @return
     */
    int update(UdsPublishEntity UdsPublishEntity);
}