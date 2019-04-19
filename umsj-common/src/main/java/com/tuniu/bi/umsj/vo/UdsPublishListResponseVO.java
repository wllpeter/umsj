package com.tuniu.bi.umsj.vo;

import java.util.List;

public class UdsPublishListResponseVO extends  BaseListResponseVO{

    private List<UdsPublishVO> udsPublishList;

    public List<UdsPublishVO> getUdsPublishList() {
        return udsPublishList;
    }

    public void setUdsPublishList(List<UdsPublishVO> udsPublishList) {
        this.udsPublishList = udsPublishList;
    }
}
