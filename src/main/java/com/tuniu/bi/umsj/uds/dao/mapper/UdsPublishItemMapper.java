package com.tuniu.bi.umsj.uds.dao.mapper;

import com.tuniu.bi.umsj.base.annotation.UmsMapper;
import com.tuniu.bi.umsj.uds.dao.entity.UdsPublishItemEntity;
import com.tuniu.bi.umsj.uds.dao.entity.UdsPublishItemParamEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhangwei21
 */
@UmsMapper
public interface UdsPublishItemMapper {

    /**
     * 查询所有
     * @param udsPublishItemParamEntity
     * @return
     */
    List<UdsPublishItemEntity> findMany(UdsPublishItemParamEntity udsPublishItemParamEntity);

    /**
     * 插入
     * @return
     */
    int insert(UdsPublishItemEntity udsPublishItemEntity);

    /**
     * 更新
     * @param udsPublishItemEntity
     */
    int update(UdsPublishItemEntity udsPublishItemEntity);

    /**
     * 删除
     * @param id
     * @return
     */
    int delete(@Param("id") Integer id);
}