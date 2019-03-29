package com.tuniu.bi.umsj.service;

import com.alibaba.fastjson.JSONObject;
import com.tuniu.bi.umsj.exception.AbstractException;
import com.tuniu.bi.umsj.vo.AlertManagerRequestVO;
import com.tuniu.bi.umsj.vo.MessageRequestVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author muchen
 */
@Service
public class WebHookServiceImpl implements WebHookService {

    private Logger logger = LoggerFactory.getLogger(WebHookServiceImpl.class);

    @Autowired
    private DingTalkService dingTalkService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SmsService smsService;

    private List<String> biPlatform = Arrays.asList("bianyuqiu"
//            ,"lujian2","wangjun9","haozhichao","weiliangliang","zhangwei21","zhangzheming"
    );

    @Override
    public void sendMessage(AlertManagerRequestVO requestVO, int businessType) throws AbstractException {
        List<String> nameList = new ArrayList<>(biPlatform);
        MessageRequestVO messageRequestVO = new MessageRequestVO();
        messageRequestVO.setTitle(requestVO.getCommonAnnotations().getSummary());
        messageRequestVO.setContent(requestVO.getCommonAnnotations().getDescription());
        messageRequestVO.setNames(nameList);
        logger.info("RequestVO: {}",JSONObject.toJSONString(requestVO));
        switch (businessType) {
            case 1:
                messageRequestVO.setType(2);
                emailService.sendMessage(messageRequestVO);
                logger.info("发送邮件成功");
                break;
            case 2:
                messageRequestVO.setType(1);
                dingTalkService.sendMessage(messageRequestVO);
                messageRequestVO.setType(2);
                emailService.sendMessage(messageRequestVO);
                logger.info("发送邮件&钉钉成功");
                break;
            case 3:
                messageRequestVO.setType(3);
                smsService.sendMessage(messageRequestVO);
                logger.info("发送短信成功");
                break;
            default:
                break;
        }
    }
}
