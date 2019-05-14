package com.tuniu.bi.umsj.base.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author zhangwei21
 */
public class UdsPublishVO {

    private Integer id;

    @NotBlank(message = "发布单主题不能为空")
    private String title;

    @NotBlank(message = "jira单号不能为空")
    private String jiraId;

    private Integer sysType;

    private Integer publishType;

    private String affectedData;

    @NotBlank(message = "ReviewBoard地址不能为空")
    private String reviewBoardUrl;

    private String publishStep;

    private String errRollback;

    private String memo;

    private String publishUser;

    private String applyUser;

    private Integer status;

    private String createdAt;

    private String updatedAt;

    @NotEmpty(message = "发布代码不能为空")
    private List<UdsPublishItemVO> udsPublishItemList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJiraId() {
        return jiraId;
    }

    public void setJiraId(String jiraId) {
        this.jiraId = jiraId;
    }

    public Integer getSysType() {
        return sysType;
    }

    public void setSysType(Integer sysType) {
        this.sysType = sysType;
    }

    public Integer getPublishType() {
        return publishType;
    }

    public void setPublishType(Integer publishType) {
        this.publishType = publishType;
    }

    public String getAffectedData() {
        return affectedData;
    }

    public void setAffectedData(String affectedData) {
        this.affectedData = affectedData;
    }

    public String getReviewBoardUrl() {
        return reviewBoardUrl;
    }

    public void setReviewBoardUrl(String reviewBoardUrl) {
        this.reviewBoardUrl = reviewBoardUrl;
    }

    public String getPublishStep() {
        return publishStep;
    }

    public void setPublishStep(String publishStep) {
        this.publishStep = publishStep;
    }

    public String getErrRollback() {
        return errRollback;
    }

    public void setErrRollback(String errRollback) {
        this.errRollback = errRollback;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPublishUser() {
        return publishUser;
    }

    public void setPublishUser(String publishUser) {
        this.publishUser = publishUser;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<UdsPublishItemVO> getUdsPublishItemList() {
        return udsPublishItemList;
    }

    public void setUdsPublishItemList(List<UdsPublishItemVO> udsPublishItemList) {
        this.udsPublishItemList = udsPublishItemList;
    }
}
