package com.tuniu.bi.umsj.service;

import com.tuniu.bi.umsj.exception.AbstractException;
import com.tuniu.bi.umsj.vo.DingTalkReqeustVO;

/**
 * @author zhangwei21
 *
 * 发送钉钉消息代理类
 */
public interface DingTalkService {

    /**
     * 钉钉发送消息
     * @param requestVO
     */
    void sendPersonalMsg(DingTalkReqeustVO requestVO) throws AbstractException;
}
