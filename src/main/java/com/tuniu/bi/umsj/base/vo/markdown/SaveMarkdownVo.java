package com.tuniu.bi.umsj.base.vo.markdown;

/**
 * @Author wll
 * @Date 2019/12/2 17:41
 * @Description
 **/
public class SaveMarkdownVo {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "SaveMarkdownVo{" +
                "content='" + content + '\'' +
                '}';
    }
}
