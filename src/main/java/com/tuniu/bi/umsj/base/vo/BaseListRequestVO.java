package com.tuniu.bi.umsj.base.vo;

/**
 * @author zhangwei21
 */
public class BaseListRequestVO {

    private Integer pageNum;

    private Integer pageSize;

    private String sortBy;

    private String order;

    public BaseListRequestVO() {
    }

    public BaseListRequestVO(Integer pageNum, Integer pageSize, String sortBy, String order) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.sortBy = sortBy;
        this.order = order;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
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
