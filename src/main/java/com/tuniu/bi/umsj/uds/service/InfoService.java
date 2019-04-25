package com.tuniu.bi.umsj.uds.service;

import com.tuniu.bi.umsj.uds.dao.entity.InfoEntity;

public interface InfoService {
    InfoEntity findByPk(Integer id);

    int insert(InfoEntity infoEntity);
}
