package com.tuniu.bi.umsj.vo;

import java.util.List;

/**
 * 钉钉消息请求
 * @author zhangwei21
 * wiki http://wiki.tuniu.org/pages/viewpage.action?pageId=82722329
 */
public class DingTalkReqeustVO {

    private Integer msgType;

    private String title;

    private String content;

    private List<Integer> uids;

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
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

    public List<Integer> getUids() {
        return uids;
    }

    public void setUids(List<Integer> uids) {
        this.uids = uids;
    }
}
