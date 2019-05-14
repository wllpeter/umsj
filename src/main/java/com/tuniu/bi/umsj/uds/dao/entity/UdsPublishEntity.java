package com.tuniu.bi.umsj.uds.dao.entity;

import java.util.Date;

public class UdsPublishEntity {
    private Integer id;

    private String title;

    private String jiraId;

    private Integer sysType;

    private Integer publishType;

    private String codeType;

    private String codePath;

    private String affectedData;

    private String reviewBoardUrl;

    private String publishStep;

    private String errRollback;

    private String memo;

    private String publishUser;

    private String applyUser;

    private Integer status;

    private String createdAt;

    private String updatedAt;

    public UdsPublishEntity() {
    }

    public UdsPublishEntity(Integer id, String title, String jiraId, Integer sysType, Integer publishType,
                            String codeType, String codePath, String affectedData, String reviewBoardUrl,
                            String publishStep, String errRollback, String memo, String publishUser,
                            String applyUser, Integer status, String createdAt, String updatedAt) {
        this.id = id;
        this.title = title;
        this.jiraId = jiraId;
        this.sysType = sysType;
        this.publishType = publishType;
        this.codeType = codeType;
        this.codePath = codePath;
        this.affectedData = affectedData;
        this.reviewBoardUrl = reviewBoardUrl;
        this.publishStep = publishStep;
        this.errRollback = errRollback;
        this.memo = memo;
        this.publishUser = publishUser;
        this.applyUser = applyUser;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

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
        this.title = title == null ? null : title.trim();
    }

    public String getJiraId() {
        return jiraId;
    }

    public void setJiraId(String jiraId) {
        this.jiraId = jiraId == null ? null : jiraId.trim();
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

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType == null ? null : codeType.trim();
    }

    public String getCodePath() {
        return codePath;
    }

    public void setCodePath(String codePath) {
        this.codePath = codePath == null ? null : codePath.trim();
    }

    public String getAffectedData() {
        return affectedData;
    }

    public void setAffectedData(String affectedData) {
        this.affectedData = affectedData == null ? null : affectedData.trim();
    }

    public String getReviewBoardUrl() {
        return reviewBoardUrl;
    }

    public void setReviewBoardUrl(String reviewBoardUrl) {
        this.reviewBoardUrl = reviewBoardUrl == null ? null : reviewBoardUrl.trim();
    }

    public String getPublishStep() {
        return publishStep;
    }

    public void setPublishStep(String publishStep) {
        this.publishStep = publishStep == null ? null : publishStep.trim();
    }

    public String getErrRollback() {
        return errRollback;
    }

    public void setErrRollback(String errRollback) {
        this.errRollback = errRollback == null ? null : errRollback.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public String getPublishUser() {
        return publishUser;
    }

    public void setPublishUser(String publishUser) {
        this.publishUser = publishUser == null ? null : publishUser.trim();
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser == null ? null : applyUser.trim();
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
}