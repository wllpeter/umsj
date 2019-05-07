package com.tuniu.bi.umsj.base.vo;

import java.util.List;

public class UserItem {

    private Integer id;

    private String username;

    private String fullName;

    private String department;

    private String createdAt;

    private String updatedAt;

    private List<RoleItem> roleItems;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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

    public List<RoleItem> getRoleItems() {
        return roleItems;
    }

    public void setRoleItems(List<RoleItem> roleItems) {
        this.roleItems = roleItems;
    }
}
