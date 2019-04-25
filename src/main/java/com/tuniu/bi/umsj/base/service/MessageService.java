package com.tuniu.bi.umsj.base.service;

import com.tuniu.bi.umsj.base.exception.AbstractException;
import com.tuniu.bi.umsj.base.vo.MessageRequestVO;

/**
 * @author zhangwei21
 */
public interface MessageService {

    /**
     * 发送消息
     * @param messageRequestVO
     * @throws AbstractException
     */
    void sendMessage(MessageRequestVO messageRequestVO) throws AbstractException;
}
