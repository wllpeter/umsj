package com.tuniu.bi.umsj.service;

import com.tuniu.bi.umsj.exception.AbstractException;
import com.tuniu.bi.umsj.vo.DingTalkRequestVO;
import com.tuniu.bi.umsj.vo.MessageRequestVO;

/**
 * @author zhangwei21
 * <p>
 * 发送钉钉消息代理类
 */
public interface DingTalkService {

    /**
     * 钉钉发送消息
     *
     * @param requestVO
     */
    void sendPersonalMsg(DingTalkRequestVO requestVO) throws AbstractException;


    /**
     * 发送消息
     *
     * @param messageRequestVO
     * @throws AbstractException
     */
    void sendMessage(MessageRequestVO messageRequestVO) throws AbstractException;

}
