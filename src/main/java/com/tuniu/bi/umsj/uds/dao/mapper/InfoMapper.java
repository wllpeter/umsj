package com.tuniu.bi.umsj.uds.dao.mapper;

import com.tuniu.bi.umsj.base.annotation.ClusterMapper;
import com.tuniu.bi.umsj.uds.dao.entity.InfoEntity;
import org.apache.ibatis.annotations.Param;

@ClusterMapper
public interface InfoMapper {
    InfoEntity findByPk(@Param("id") Integer id);

    int insert(InfoEntity infoEntity);
}
