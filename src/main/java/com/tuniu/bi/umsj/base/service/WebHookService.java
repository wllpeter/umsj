package com.tuniu.bi.umsj.base.service;

import com.tuniu.bi.umsj.base.exception.AbstractException;
import com.tuniu.bi.umsj.base.vo.AlertManagerRequestVO;

/**
 * @author muchen
 * <p>
 * webHook
 */
public interface WebHookService {

    /**
     * 处理webHook的消息
     *
     * @param requestVO
     */
    void sendMessage(AlertManagerRequestVO requestVO) throws AbstractException;


}
