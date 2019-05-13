package com.tuniu.bi.umsj.uds.dao.entity;

/**
 * @author zhangwei21
 */
public class UdsPublishLogEntity {
    private Integer id;

    private Integer publishId;

    private String content;

    private String data;

    private String createdAt;

    public UdsPublishLogEntity() {
    }

    public UdsPublishLogEntity(Integer id, Integer publishId, String content, String data, String createdAt) {
        this.id = id;
        this.publishId = publishId;
        this.content = content;
        this.data = data;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPublishId() {
        return publishId;
    }

    public void setPublishId(Integer publishId) {
        this.publishId = publishId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
