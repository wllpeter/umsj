package com.tuniu.bi.umsj.base.entitydo;

import java.util.List;

/**
 * @author zhangwei21
 */
public class DingTalkResponseDO extends AbstractResponseDO {

    private List<Integer> data;


    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DingTalkResponseDO{" +
                "data=" + data +
                '}';
    }
}
