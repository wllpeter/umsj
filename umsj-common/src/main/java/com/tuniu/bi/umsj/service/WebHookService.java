package com.tuniu.bi.umsj.service;

import com.tuniu.bi.umsj.exception.AbstractException;
import com.tuniu.bi.umsj.vo.AlertManagerRequestVO;
import com.tuniu.bi.umsj.vo.DingTalkRequestVO;
import com.tuniu.bi.umsj.vo.MessageRequestVO;

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
    void sendMessage(AlertManagerRequestVO requestVO,int businessType) throws AbstractException;


}
