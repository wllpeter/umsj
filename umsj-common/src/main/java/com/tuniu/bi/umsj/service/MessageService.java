package com.tuniu.bi.umsj.service;

import com.tuniu.bi.umsj.exception.AbstractException;
import com.tuniu.bi.umsj.vo.MessageRequestVO;

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
