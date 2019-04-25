/**
 * Copyright (C) 2006-2018 Tuniu All rights reserved
 */
package com.tuniu.bi.umsj.base.entitydo;

import java.util.List;

/**
 * uc根据id&姓名查询详细信息
 *
 * @author zhangwei21
 */
public class UcQuerySalesRequestDO {
    private String subSystem;
    private String key;
    private Data data;

    public String getSubSystem() {
        return subSystem;
    }

    public void setSubSystem(String subSystem) {
        this.subSystem = subSystem;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private String service;
        private List<Condition> cond;

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public List<Condition> getCond() {
            return cond;
        }

        public void setCond(List<Condition> cond) {
            this.cond = cond;
        }
    }

    public static class Condition {
        private int type;
        private String value;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
