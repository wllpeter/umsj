package com.tuniu.bi.umsj.base.dao.entity;

import java.util.List;

/**
 * @author zhangwei21
 */
public class RolesParamEntity {
    private Integer id;

    private String code;

    private String name;

    private String menus;

    private String submenus;

    private String actions;

    private List<String> codes;

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
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getMenus() {
        return menus;
    }

    public void setMenus(String menus) {
        this.menus = menus == null ? null : menus.trim();
    }

    public String getSubmenus() {
        return submenus;
    }

    public void setSubmenus(String submenus) {
        this.submenus = submenus == null ? null : submenus.trim();
    }

    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions == null ? null : actions.trim();
    }

    public List<String> getCodes() {
        return codes;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }
}