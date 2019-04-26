package com.tuniu.bi.umsj.base.vo;

import com.tuniu.bi.umsj.base.dao.entity.UserEntity;

import java.util.List;

public class UserListResponseVO extends BaseListResponseVO {

    private List<UserEntity> userList;

    public List<UserEntity> getUserList() {
        return userList;
    }

    public void setUserList(List<UserEntity> userList) {
        this.userList = userList;
    }
}
