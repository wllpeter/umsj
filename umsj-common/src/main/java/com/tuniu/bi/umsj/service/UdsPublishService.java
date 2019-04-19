package com.tuniu.bi.umsj.service;

import com.tuniu.bi.umsj.vo.UdsPublishVO;
import com.tuniu.bi.umsj.vo.UdsPublishListRequestVO;
import com.tuniu.bi.umsj.vo.UdsPublishListResponseVO;

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
