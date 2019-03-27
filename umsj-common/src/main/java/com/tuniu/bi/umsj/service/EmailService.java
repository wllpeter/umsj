package com.tuniu.bi.umsj.service;

import com.tuniu.bi.umsj.exception.AbstractException;
import com.tuniu.bi.umsj.vo.EmailRequestVO;
import com.tuniu.bi.umsj.vo.MessageRequestVO;

/**
 * 邮件服务
 */
public interface EmailService {

    /**
     * 发送邮件
     * @param emailRequestVO
     */
    void sendEmail(EmailRequestVO emailRequestVO);

    /**
     * 发送消息
     * @param messageRequestVO
     * @throws AbstractException
     */
    void sendMessage(MessageRequestVO messageRequestVO) throws AbstractException;
}
