package com.tuniu.bi.umsj.base.vo;

public class UserListRequestVO extends BaseListRequestVO {

    private String username;

    private String sortBy;

    private String order;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
