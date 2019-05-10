package com.tuniu.bi.umsj.base.vo;

/**
 * @author zhangwei21
 */
public class UdsPublishListRequestVO extends BaseListRequestVO {

    private String applyUser;

    private String publishUser;

    private Integer status;

    private String jiraId;

    private String title;

    public UdsPublishListRequestVO() {
    }

    public UdsPublishListRequestVO(Integer pageNum, Integer pageSize, String sortBy, String order, String applyUser, String publishUser, Integer status, String jiraId, String title) {
        super(pageNum, pageSize, sortBy, order);
        this.applyUser = applyUser;
        this.publishUser = publishUser;
        this.status = status;
        this.jiraId = jiraId;
        this.title = title;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public String getPublishUser() {
        return publishUser;
    }

    public void setPublishUser(String publishUser) {
        this.publishUser = publishUser;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getJiraId() {
        return jiraId;
    }

    public void setJiraId(String jiraId) {
        this.jiraId = jiraId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
