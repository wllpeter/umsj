package com.tuniu.bi.umsj.service;

import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.tuniu.bi.umsj.utils.StrUtils;
import com.tuniu.bi.umsj.vo.EmailRequestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 发送邮件
 *
 * @author zhangwei21
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Value("${mail.sender}")
    private String sendMail;


    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(EmailRequestVO emailRequestVO) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(emailRequestVO.getEmails().toArray(new String[emailRequestVO.getEmails().size()]));
        simpleMailMessage.setFrom(sendMail);
        simpleMailMessage.setSubject(emailRequestVO.getSubject());
        simpleMailMessage.setText(emailRequestVO.getContent());
        javaMailSender.send(simpleMailMessage);
    }
}
