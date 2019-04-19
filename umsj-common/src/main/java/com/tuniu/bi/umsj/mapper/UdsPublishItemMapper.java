package com.tuniu.bi.umsj.mapper;

import com.tuniu.bi.umsj.mapper.entity.UdsPublishItemEntity;
import com.tuniu.bi.umsj.mapper.entity.UdsPublishItemParamEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhangwei21
 */
@Mapper
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