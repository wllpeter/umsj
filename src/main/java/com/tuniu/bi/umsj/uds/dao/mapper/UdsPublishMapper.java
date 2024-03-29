package com.tuniu.bi.umsj.uds.dao.mapper;

import com.tuniu.bi.umsj.base.annotation.UmsMapper;
import com.tuniu.bi.umsj.uds.dao.entity.UdsPublishEntity;
import com.tuniu.bi.umsj.uds.dao.entity.UdsPublishParamEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhangwei21
 */
@UmsMapper
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

    /**
     * 根据主键查找
     * @param id
     * @return
     */
    UdsPublishEntity findByPk(@Param("id")Integer id);
}