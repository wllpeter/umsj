package com.tuniu.bi.umsj.uds.service;

import com.tuniu.bi.umsj.base.vo.UdsPublishListRequestVO;
import com.tuniu.bi.umsj.base.vo.UdsPublishListResponseVO;
import com.tuniu.bi.umsj.base.vo.UdsPublishVO;

/**
 * @author zhangwei21
 */
public interface UdsPublishService {

    /**
     * 查询多个
     * @param udsPublishQueryVO
     * @return
     */
    UdsPublishListResponseVO findMany(UdsPublishListRequestVO udsPublishQueryVO);

    /**
     * 创建发布单
     * @param udsPublishVO
     */
    void createPublish(UdsPublishVO udsPublishVO);

    /**
     * 更新发布单内容
     * @param udsPublishVO
     */
    void updatePublish(UdsPublishVO udsPublishVO);

    /**
     * 更新发布单的状态
     * @param udsPublishVO
     */
    void updatePublishStatus(UdsPublishVO udsPublishVO);
}
