package com.tuniu.bi.umsj.config;

import com.tuniu.bi.umsj.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangwei21
 */
@Component
public class SendMessageConfig {

    @Autowired
    @Qualifier("dingTalk")
    private MessageService dingTalkService;

    @Autowired
    @Qualifier("email")
    private MessageService emailService;

    @Autowired
    @Qualifier("sms")
    private MessageService smsService;

    @Bean("sendMessageMap")
    public Map<Integer, MessageService> sendMessageMap() {
        Map<Integer, MessageService> ret = new HashMap<>(16);
        ret.put(1, dingTalkService);
        ret.put(2, emailService);
        ret.put(3, smsService);
        return ret;
    }
}
