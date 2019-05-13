package com.tuniu.bi.umsj.uds.dao.entity;

/**
 * @author zhangwei21
 */
public class UdsPublishLogParamEntity {

    private Integer publishId;

    private String content;

    private String data;


    public UdsPublishLogParamEntity() {
    }

    public UdsPublishLogParamEntity(Integer publishId, String content, String data) {
        this.publishId = publishId;
        this.content = content;
        this.data = data;
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

}
