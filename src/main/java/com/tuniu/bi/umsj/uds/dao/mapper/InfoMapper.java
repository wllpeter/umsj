package com.tuniu.bi.umsj.uds.dao.mapper;

import com.tuniu.bi.umsj.base.annotation.FlushCmMapper;
import com.tuniu.bi.umsj.uds.dao.entity.InfoEntity;
import org.apache.ibatis.annotations.Param;

@FlushCmMapper
public interface InfoMapper {
    InfoEntity findByPk(@Param("id") Integer id);

    int insert(InfoEntity infoEntity);
}
