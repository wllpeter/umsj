package com.tuniu.bi.umsj.base.vo;

import javax.validation.constraints.NotNull;

public class DeleteRoleRequestVO {

    @NotNull(message = "角色id不能为空")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
