package com.tuniu.bi.umsj.service;

import com.tuniu.bi.umsj.exception.AbstractException;
import com.tuniu.bi.umsj.exception.InvalidParamException;
import com.tuniu.bi.umsj.mapper.UserMapper;
import com.tuniu.bi.umsj.vo.EmailRequestVO;
import com.tuniu.bi.umsj.vo.MessageRequestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;
    @Override
    public void sendEmail(EmailRequestVO emailRequestVO) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(emailRequestVO.getEmails().toArray(new String[emailRequestVO.getEmails().size()]));
        simpleMailMessage.setFrom(sendMail);
        simpleMailMessage.setSubject(emailRequestVO.getSubject());
        simpleMailMessage.setText(emailRequestVO.getContent());
        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public void sendMessage(MessageRequestVO messageRequestVO) throws AbstractException {
        // 查询user表,查询salerId，phone
        List<String> emails = userService.obtainReceiver(messageRequestVO.getType(), messageRequestVO.getNames());
        if (CollectionUtils.isEmpty(emails)) {
            throw new InvalidParamException("用户的email为空");
        }
        EmailRequestVO emailRequestVO = new EmailRequestVO();
        emailRequestVO.setEmails(emails);
        emailRequestVO.setSubject(messageRequestVO.getTitle());
        emailRequestVO.setContent(messageRequestVO.getContent());
        sendEmail(emailRequestVO);
    }
}
