package com.tuniu.bi.umsj.vo;

import java.util.List;

/**
 * @author zhangwei21
 */
public class MessageRequestVO {

    private List<String> names;

    /**
     * 发送的消息类型1:钉钉 2：邮件 3：短信
     */
    private Integer type;

    private String title;

    private String content;

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
