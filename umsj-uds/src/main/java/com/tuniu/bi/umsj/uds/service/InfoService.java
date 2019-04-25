package com.tuniu.bi.umsj.uds.service;

import com.tuniu.bi.umsj.mapper.entity.UserEntity;
import com.tuniu.bi.umsj.uds.mapper.entity.InfoEntity;

public interface InfoService {
    InfoEntity findByPk(Integer id);

    int insert(InfoEntity infoEntity);
}
