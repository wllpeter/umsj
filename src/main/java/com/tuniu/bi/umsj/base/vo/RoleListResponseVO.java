package com.tuniu.bi.umsj.base.vo;

import java.util.List;

public class RoleListResponseVO extends BaseListResponseVO {

    private List<RoleItem> roleList;

    public List<RoleItem> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleItem> roleList) {
        this.roleList = roleList;
    }
}
