package com.tuniu.bi.umsj.base.vo;

import javax.validation.constraints.NotBlank;

public class UdsPublishItemVO {

    private Integer id;

    private Integer publishId;

    @NotBlank(message = "代码类型不能为空")
    private String codeType;

    @NotBlank(message = "代码路径不能为空")
    private String codePath;

    /**
     * 1：未发布 2：发布成功 3：发布失败
     */
    private Integer state;

    private String createdAt;

    private String updatedAt;

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
        this.codeType = codeType;
    }

    public String getCodePath() {
        return codePath;
    }

    public void setCodePath(String codePath) {
        this.codePath = codePath;
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
