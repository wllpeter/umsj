package com.tuniu.bi.umsj.base.vo;

import java.util.List;

/**
 * @author zhangwei21
 */
public class AlertManagerRequestVO {

    /**
     * web\.hook
     */
    private String receiver;

    /**
     * firing
     */
    private String status;

    private List<AlertItem> alerts;

    private GroupLabel groupLabels;

    private CommonLabel commonLabels;

    private CommonAnnotation commonAnnotations;

    private String externalURL;

    private String version;

    private String groupKey;

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<AlertItem> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<AlertItem> alerts) {
        this.alerts = alerts;
    }

    public GroupLabel getGroupLabels() {
        return groupLabels;
    }

    public void setGroupLabels(GroupLabel groupLabels) {
        this.groupLabels = groupLabels;
    }

    public CommonLabel getCommonLabels() {
        return commonLabels;
    }

    public void setCommonLabels(CommonLabel commonLabels) {
        this.commonLabels = commonLabels;
    }

    public CommonAnnotation getCommonAnnotations() {
        return commonAnnotations;
    }

    public void setCommonAnnotations(CommonAnnotation commonAnnotations) {
        this.commonAnnotations = commonAnnotations;
    }

    public String getExternalURL() {
        return externalURL;
    }

    public void setExternalURL(String externalURL) {
        this.externalURL = externalURL;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }
}
