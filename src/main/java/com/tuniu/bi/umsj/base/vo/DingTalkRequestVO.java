package com.tuniu.bi.umsj.base.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 钉钉消息请求
 * @author zhangwei21
 * wiki http://wiki.tuniu.org/pages/viewpage.action?pageId=82722329
 */
public class DingTalkRequestVO {

    @NotNull(message = "发送的消息类型不能为空")
    private Integer msgType;

    private String title;

    @NotBlank(message = "发送的内容不能为空")
    private String content;

    @NotEmpty(message = "钉钉发送人员不能为空")
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
