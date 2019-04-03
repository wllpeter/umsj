/**
 * Copyright (C) 2006-2018 Tuniu All rights reserved
 */
package com.tuniu.bi.umsj.entitydo;
import java.util.Map;

/**
 * @author zhangwei21
 */
public class UcQuerySalesResponseDO extends AbstractResponseDO {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private int length;
        private Map<String, Employees> sales;

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public Map<String, Employees> getSales() {
            return sales;
        }

        public void setSales(Map<String, Employees> sales) {
            this.sales = sales;
        }
    }

    public static class Employees {

        private Integer salerId;

        private String name;

        private String spelling;

        private String workNum;

        private String dept;

        private String cellPhone;


        public Integer getSalerId() {
            return salerId;
        }

        public void setSalerId(Integer salerId) {
            this.salerId = salerId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSpelling() {
            return spelling;
        }

        public void setSpelling(String spelling) {
            this.spelling = spelling;
        }

        public String getWorkNum() {
            return workNum;
        }

        public void setWorkNum(String workNum) {
            this.workNum = workNum;
        }

        public String getDept() {
            return dept;
        }

        public void setDept(String dept) {
            this.dept = dept;
        }

        public String getCellPhone() {
            return cellPhone;
        }

        public void setCellPhone(String cellPhone) {
            this.cellPhone = cellPhone;
        }
    }

    @Override
    public String toString() {
        return "UcQuerySalesResponseDO{" +
                "data=" + data +
                '}';
    }
}