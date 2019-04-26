package com.tuniu.bi.umsj.base.vo;

import javax.validation.constraints.NotBlank;

/**
 * @author zhangwei21
 */
public class UserRequestVO {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "用户角色信息不能为空")
    private String roleCodes;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoleCodes() {
        return roleCodes;
    }

    public void setRoleCodes(String roleCodes) {
        this.roleCodes = roleCodes;
    }
}
