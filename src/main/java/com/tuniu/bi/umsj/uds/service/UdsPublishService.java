package com.tuniu.bi.umsj.uds.service;

import com.tuniu.bi.umsj.base.exception.AbstractException;
import com.tuniu.bi.umsj.base.vo.*;

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
     * @param requestVO
     * @param username
     */
    void createPublish(CreateUdsRequestVO requestVO, String username) throws AbstractException;

    /**
     * 更新发布单内容
     * @param updateUdsRequestVO
     */
    void updatePublish(UpdateUdsRequestVO updateUdsRequestVO, String username) throws AbstractException;

    /**
     * 更新发布单的状态
     * @param updateUdsStatusRequestVO
     * @param username
     */
    void updatePublishStatus(UpdateUdsStatusRequestVO updateUdsStatusRequestVO, String username) throws AbstractException;

    /**
     * uds详情接口
     * @param id
     */
    UdsPublishVO publishDetail(Integer id) throws AbstractException;
}
