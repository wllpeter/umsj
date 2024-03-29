package com.tuniu.bi.umsj.uds.dao.entity;

/**
 * @author zhangwei21
 */
public class UdsPublishItemParamEntity {
    private Integer id;

    private Integer publishId;

    private String codeType;

    private String codePath;

    private Integer state;

    public UdsPublishItemParamEntity() {
    }

    public UdsPublishItemParamEntity(Integer id, Integer publishId, String codeType, String codePath, Integer state) {
        this.id = id;
        this.publishId = publishId;
        this.codeType = codeType;
        this.codePath = codePath;
        this.state = state;
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

}