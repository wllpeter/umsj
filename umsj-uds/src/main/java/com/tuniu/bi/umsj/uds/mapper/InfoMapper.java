package com.tuniu.bi.umsj.uds.mapper;

import com.tuniu.bi.umsj.annotation.ClusterMapper;
import com.tuniu.bi.umsj.uds.mapper.entity.InfoEntity;
import org.apache.ibatis.annotations.Param;

@ClusterMapper
public interface InfoMapper {
    InfoEntity findByPk(@Param("id") Integer id);

    int insert(InfoEntity infoEntity);
}
