package com.tuniu.bi.umsj.service;

import com.tuniu.bi.umsj.vo.EmailRequestVO;

/**
 * 邮件服务
 */
public interface EmailService {

    /**
     * 发送邮件
     * @param emailRequestVO
     */
    void sendEmail(EmailRequestVO emailRequestVO);
}
