package com.tuniu.bi.umsj.uds.dao.entity;

/**
 * @author zhangwei21
 */
public class UdsPublishItemEntity {
    private Integer id;

    private Integer publishId;

    private String codeType;

    private String codePath;

    private Integer state;

    private String createdAt;

    private String updatedAt;

    public UdsPublishItemEntity() {
    }

    public UdsPublishItemEntity(Integer id, Integer publishId, String codeType,
                                String codePath, Integer state, String createdAt, String updatedAt) {
        this.id = id;
        this.publishId = publishId;
        this.codeType = codeType;
        this.codePath = codePath;
        this.state = state;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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