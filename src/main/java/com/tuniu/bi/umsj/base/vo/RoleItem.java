package com.tuniu.bi.umsj.base.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class RoleItem {

    private Integer id;

    @NotBlank(message = "角色编码不能为空")
    private String code;

    @NotBlank(message = "角色名称不能为空")
    private String name;

    @NotEmpty(message = "菜单不能为空")
    private List<String> menus;

    @NotEmpty(message = "子菜单不能为空")
    private List<String> subMenus;

    @NotEmpty(message = "操作权限不能为空")
    private List<String> actions;

    private String createdAt;

    private String updatedAt;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<String> getMenus() {
        return menus;
    }

    public void setMenus(List<String> menus) {
        this.menus = menus;
    }

    public List<String> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(List<String> subMenus) {
        this.subMenus = subMenus;
    }

    public List<String> getActions() {
        return actions;
    }

    public void setActions(List<String> actions) {
        this.actions = actions;
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
