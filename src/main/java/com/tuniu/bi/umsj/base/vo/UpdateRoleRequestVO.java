package com.tuniu.bi.umsj.base.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class UpdateRoleRequestVO {

    @NotNull(message = "角色id不能为空")
    private Integer id;

    @NotBlank(message = "角色编码不能为空")
    private String code;

    @NotBlank(message = "角色名称不能为空")
    private String name;

    @NotEmpty(message = "菜单不能为空")
    private List<String> menus;

    private List<String> subMenus;

    @NotEmpty(message = "操作权限不能为空")
    private List<String> actions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
}
