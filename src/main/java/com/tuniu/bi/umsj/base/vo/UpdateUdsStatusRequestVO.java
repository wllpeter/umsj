package com.tuniu.bi.umsj.base.vo;

import javax.validation.constraints.NotNull;

/**
 * @author zhangwei21
 */
public class UpdateUdsStatusRequestVO {

    @NotNull(message = "发布单id不能为空")
    private Integer id;

    @NotNull(message = "发布单状态不能为空")
    private Integer status;

    public UpdateUdsStatusRequestVO() {
    }

    public UpdateUdsStatusRequestVO(Integer id, Integer status) {
        this.id = id;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
