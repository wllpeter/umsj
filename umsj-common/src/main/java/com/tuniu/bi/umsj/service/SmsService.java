package com.tuniu.bi.umsj.service;

import com.tuniu.bi.umsj.exception.AbstractException;
import com.tuniu.bi.umsj.vo.SmsRequestVO;

/**
 * @author zhangwei21
 */
public interface SmsService {
    /**
     * 发送短信
     * @param smsRequestVO
     * @throws AbstractException
     */
    void sendSms(SmsRequestVO smsRequestVO) throws AbstractException;

}
