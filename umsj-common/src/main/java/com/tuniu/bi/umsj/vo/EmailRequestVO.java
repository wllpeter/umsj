package com.tuniu.bi.umsj.vo;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author zhangwei21
 */
public class EmailRequestVO {

    @NotBlank(message = "邮件主题不能为空")
    private String subject;

    @NotBlank(message = "邮件内容不能为空")
    private String content;

    @NotEmpty(message = "邮件发送人员不能为空")
    private List<String> emails;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    @Override
    public String toString() {
        return "EmailRequestVO{" +
                "subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", emails=" + emails +
                '}';
    }
}
