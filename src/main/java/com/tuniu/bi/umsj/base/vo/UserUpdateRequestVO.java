package com.tuniu.bi.umsj.base.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zhangwei21
 */
public class UserUpdateRequestVO {

    @NotNull(message = "用户id不能为空!")
    private Integer id;
    @NotEmpty(message = "角色不能为空!")
    private List<String> roleCodes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<String> getRoleCodes() {
        return roleCodes;
    }

    public void setRoleCodes(List<String> roleCodes) {
        this.roleCodes = roleCodes;
    }
}
