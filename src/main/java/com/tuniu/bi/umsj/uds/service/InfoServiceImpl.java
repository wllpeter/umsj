package com.tuniu.bi.umsj.uds.service;

import com.tuniu.bi.umsj.uds.dao.entity.InfoEntity;
import com.tuniu.bi.umsj.uds.dao.mapper.InfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InfoServiceImpl implements InfoService {

    @Autowired
    private InfoMapper infoMapper;
    @Override
    public InfoEntity findByPk(Integer id) {
        return infoMapper.findByPk(id);
    }

    @Override
    @Transactional(transactionManager="clusterTransactionManager",rollbackFor = Exception.class)
    public int insert(InfoEntity infoEntity) {
        infoMapper.insert(infoEntity);
        String s = null;
        //System.out.println(s.toString());
        return 0;
    }
}
