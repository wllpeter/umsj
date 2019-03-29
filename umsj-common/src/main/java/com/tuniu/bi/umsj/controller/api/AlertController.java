package com.tuniu.bi.umsj.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.tuniu.bi.umsj.exception.AbstractException;
import com.tuniu.bi.umsj.exception.InvalidParamException;
import com.tuniu.bi.umsj.service.DingTalkService;
import com.tuniu.bi.umsj.service.EmailService;
import com.tuniu.bi.umsj.service.SmsService;
import com.tuniu.bi.umsj.utils.ResponseUtils;
import com.tuniu.bi.umsj.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import com.tuniu.bi.umsj.service.WebHookService;


/**
 * 告警api(集成钉钉，邮箱，微信)
 *
 * @author zhangwei21
 */
@RestController
@RequestMapping(value = "/api/alert")
public class AlertController {

    @Autowired
    private DingTalkService dingTalkService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private WebHookService webHookService;

    /**
     * 发送钉钉消息
     *
     * @param requestVO
     * @return
     * @throws AbstractException
     */
    @RequestMapping(value = "/sendDingTalk", method = RequestMethod.POST)
    public Response sendDingDing(@RequestBody DingTalkRequestVO requestVO) throws AbstractException {
        dingTalkService.sendPersonalMsg(requestVO);
        return ResponseUtils.success();
    }

    /**
     * 发送邮件
     *
     * @param emailRequestVO
     * @return
     * @throws AbstractException
     */
    @RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
    public Response sendEmail(@RequestBody @Valid EmailRequestVO emailRequestVO) throws AbstractException {
        emailService.sendEmail(emailRequestVO);
        return ResponseUtils.success();
    }

    /**
     * 发送短信
     *
     * @param smsRequestVO
     * @return
     * @throws AbstractException
     */
    @RequestMapping(value = "/sendSms", method = RequestMethod.POST)
    public Response sendEmail(@RequestBody @Valid SmsRequestVO smsRequestVO) throws AbstractException {
        smsService.sendSms(smsRequestVO);
        return ResponseUtils.success();
    }

    /**
     * 发送短信
     *
     * @param messageRequestVO
     * @return
     * @throws AbstractException
     */
    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    public Response sendMessage(@RequestBody MessageRequestVO messageRequestVO) throws AbstractException {
        if (messageRequestVO == null || messageRequestVO.getType() == null || Strings.isNullOrEmpty(messageRequestVO.getContent())
                || CollectionUtils.isEmpty(messageRequestVO.getNames())) {
            throw new InvalidParamException();
        }
        Integer type = messageRequestVO.getType();
        switch (type) {
            case 1:
                dingTalkService.sendMessage(messageRequestVO);
                break;
            case 2:
                emailService.sendMessage(messageRequestVO);
                break;
            case 3:
                smsService.sendMessage(messageRequestVO);
                break;
            default:
                throw new InvalidParamException("暂不支持的发消息类型");
        }

        return ResponseUtils.success();
    }

    /**
     * webHookEmail
     *
     * @param requestVO
     * @return
     * @throws AbstractException
     */
    @RequestMapping(value = "/webHookEmail", method = RequestMethod.POST)
    public Response webHookEmail(@RequestBody AlertManagerRequestVO requestVO) throws AbstractException {
        System.out.println(JSONObject.toJSONString(requestVO));
        webHookService.sendMessage(requestVO,1);
        return ResponseUtils.success();
    }

    /**
     * webHookEmailSms
     *
     * @param requestVO
     * @return
     * @throws AbstractException
     */
    @RequestMapping(value = "/webHookEmailSms", method = RequestMethod.POST)
    public Response webHookEmailSms(@RequestBody AlertManagerRequestVO requestVO) throws AbstractException {
        System.out.println(JSONObject.toJSONString(requestVO));
        webHookService.sendMessage(requestVO,2);
        return ResponseUtils.success();
    }

}
