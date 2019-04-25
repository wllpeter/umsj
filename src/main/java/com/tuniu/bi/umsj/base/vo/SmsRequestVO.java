package com.tuniu.bi.umsj.base.vo;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author zhangwei21
 */
public class SmsRequestVO {

    @NotBlank(message = "消息内容不能为空")
    @Length(min = 1,max = 350, message = "消息长度不能大于350")
    private String content;

    @NotEmpty(message = "电话号码不能为空")
    private List<String> mobileNum;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(List<String> mobileNum) {
        this.mobileNum = mobileNum;
    }

    @Override
    public String toString() {
        return "SmsRequestVO{" +
                "content='" + content + '\'' +
                ", mobileNum=" + mobileNum +
                '}';
    }
}
