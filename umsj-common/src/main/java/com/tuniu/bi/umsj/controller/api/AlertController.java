package com.tuniu.bi.umsj.controller.api;

import com.tuniu.bi.umsj.exception.AbstractException;
import com.tuniu.bi.umsj.service.DingTalkService;
import com.tuniu.bi.umsj.service.EmailService;
import com.tuniu.bi.umsj.service.SmsService;
import com.tuniu.bi.umsj.utils.ResponseUtils;
import com.tuniu.bi.umsj.vo.DingTalkRequestVO;
import com.tuniu.bi.umsj.vo.EmailRequestVO;
import com.tuniu.bi.umsj.vo.Response;
import com.tuniu.bi.umsj.vo.SmsRequestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
}
