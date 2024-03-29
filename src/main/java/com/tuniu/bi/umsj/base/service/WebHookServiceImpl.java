package com.tuniu.bi.umsj.base.service;

import com.alibaba.fastjson.JSONObject;
import com.tuniu.bi.umsj.base.constant.Symbol;
import com.tuniu.bi.umsj.base.exception.AbstractException;
import com.tuniu.bi.umsj.base.vo.AlertItem;
import com.tuniu.bi.umsj.base.vo.AlertManagerRequestVO;
import com.tuniu.bi.umsj.base.vo.MessageRequestVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author muchen
 */
@Service
public class WebHookServiceImpl implements WebHookService {

    private Logger logger = LoggerFactory.getLogger(WebHookServiceImpl.class);

    private List<String> biPlatform = Arrays.asList(
            "bianyuqiu",
            "lujian2",
            "wangjun9"
    );

    private List<String> msgType = Arrays.asList(
            //钉钉
            "1",
            //邮件
            "2",
            //短信
            "3");

    @Autowired
    @Qualifier("sendMessageMap")
    private Map<Integer, MessageService> sendMessageMap;

    @Override
    public void sendMessage(AlertManagerRequestVO requestVO) throws AbstractException {
        List<String> nameList = new ArrayList<>(biPlatform);
        List<AlertItem> alertItems = requestVO.getAlerts();

        //判断alertName包含发送消息类型
        Set<String> sendTypes = new HashSet<>();
        String alertName = requestVO.getGroupLabels().getAlertname();
        if (!StringUtils.isEmpty(alertName)) {
            char[] typeArr = alertName.toCharArray();
            for (char cha : typeArr) {
                String chaStr = String.valueOf(cha);
                if (msgType.contains(chaStr)) {
                    sendTypes.add(chaStr);
                }
            }
            if (sendTypes.isEmpty()) {
                //如果没有type,则默认发送邮件
                sendTypes.add(Symbol.TWO);
            }
        }
        if (sendTypes.contains(Symbol.ONE) || sendTypes.contains(Symbol.THREE)) {
            nameList.remove("wangjun9");
            nameList.remove("lujian2");
        }
        for (AlertItem item : alertItems) {
            MessageRequestVO messageRequestVO = new MessageRequestVO();
            messageRequestVO.setTitle(item.getAnnotations().getSummary());
            messageRequestVO.setContent(item.getAnnotations().getDescription());
            messageRequestVO.setNames(nameList);
            logger.info("发送消息体: RequestVO: {}", JSONObject.toJSONString(messageRequestVO));

            for (String str : sendTypes) {
                MessageService messageService = sendMessageMap.get(Integer.valueOf(str));
                switch (str) {
                    case "1":
                        messageRequestVO.setType(1);
                        messageService.sendMessage(messageRequestVO);
                        logger.info("发送钉钉成功");
                        System.out.println("发送钉钉成功");
                        break;
                    case "2":
                        messageRequestVO.setType(2);
                        messageService.sendMessage(messageRequestVO);
                        logger.info("发送邮件成功");
                        System.out.println("发送邮件成功");
                        break;
                    case "3":
                        messageRequestVO.setType(3);
                        messageService.sendMessage(messageRequestVO);
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
