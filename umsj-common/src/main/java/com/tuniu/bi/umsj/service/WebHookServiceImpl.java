package com.tuniu.bi.umsj.service;

import com.alibaba.fastjson.JSONObject;
import com.tuniu.bi.umsj.exception.AbstractException;
import com.tuniu.bi.umsj.vo.AlertItem;
import com.tuniu.bi.umsj.vo.AlertManagerRequestVO;
import com.tuniu.bi.umsj.vo.MessageRequestVO;
import io.micrometer.core.instrument.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    private List<String> msgType = Arrays.asList("1", "2", "3");

    @Override
    public void sendMessage(AlertManagerRequestVO requestVO) throws AbstractException {
        List<String> nameList = new ArrayList<>(biPlatform);
        List<AlertItem> alertItems = requestVO.getAlerts();

        //判断alertName包含发送消息类型
        Set<String> sendTypes = new HashSet<>();
        String alertName = requestVO.getGroupLabels().getAlertname();
        if (StringUtils.isNotBlank(alertName)) {
            char[] typeArr = alertName.toCharArray();
            for (char cha : typeArr) {
                String chaStr = String.valueOf(cha);
                if (msgType.contains(chaStr)) {
                    sendTypes.add(chaStr);
                }
            }
            if (sendTypes.isEmpty()) {
                //如果没有type,则默认发送邮件
                sendTypes.add("2");
            }
        }
        for (AlertItem item : alertItems) {
            MessageRequestVO messageRequestVO = new MessageRequestVO();
            messageRequestVO.setTitle(item.getAnnotations().getSummary());
            messageRequestVO.setContent(item.getAnnotations().getDescription());
            messageRequestVO.setNames(nameList);
            logger.info("发送消息体: RequestVO: {}", JSONObject.toJSONString(messageRequestVO));
            for (String str : sendTypes) {
                switch (str) {
                    case "1":
                        messageRequestVO.setType(1);
                        dingTalkService.sendMessage(messageRequestVO);
                        logger.info("发送钉钉成功");
                        System.out.println("发送钉钉成功");
                        break;
                    case "2":
                        messageRequestVO.setType(2);
                        emailService.sendMessage(messageRequestVO);
                        logger.info("发送邮件成功");
                        System.out.println("发送邮件成功");
                        break;
                    case "3":
                        messageRequestVO.setType(3);
                        smsService.sendMessage(messageRequestVO);
                        logger.info("发送短信成功");
                        System.out.println("发送短信成功");
                        break;
                    default:
                        break;
                }
            }

        }
    }
}
