package com.tuniu.bi.umsj.base.vo;

import com.tuniu.bi.umsj.base.dao.entity.UserEntity;

import java.util.List;

public class UserListResponseVO extends BaseListResponseVO {

    private List<UserItem> userList;

    public List<UserItem> getUserList() {
        return userList;
    }

    public void setUserList(List<UserItem> userList) {
        this.userList = userList;
    }
}
