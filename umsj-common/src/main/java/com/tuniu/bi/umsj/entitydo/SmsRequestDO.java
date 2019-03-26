package com.tuniu.bi.umsj.entitydo;

import java.util.List;

/**
 * @author zhangwei21
 */
public class SmsRequestDO {

    private Integer templateId;

    private Integer systemId;

    private List<String> mobileNum;

    private String clientIp;

    private List<SmsTemplateParam> smsTemplateParams;

    public static class SmsTemplateParam {
        private String paramKey;

        private String paramValue;

        public String getParamKey() {
            return paramKey;
        }

        public void setParamKey(String paramKey) {
            this.paramKey = paramKey;
        }

        public String getParamValue() {
            return paramValue;
        }

        public void setParamValue(String paramValue) {
            this.paramValue = paramValue;
        }

        @Override
        public String toString() {
            return "SmsTemplateParam{" +
                    "paramKey='" + paramKey + '\'' +
                    ", paramValue='" + paramValue + '\'' +
                    '}';
        }
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    public List<String> getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(List<String> mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public List<SmsTemplateParam> getSmsTemplateParams() {
        return smsTemplateParams;
    }

    public void setSmsTemplateParams(List<SmsTemplateParam> smsTemplateParams) {
        this.smsTemplateParams = smsTemplateParams;
    }

    @Override
    public String toString() {
        return "SmsRequestDO{" +
                "templateId=" + templateId +
                ", systemId=" + systemId +
                ", mobileNum=" + mobileNum +
                ", clientIp='" + clientIp + '\'' +
                ", smsTemplateParams=" + smsTemplateParams +
                '}';
    }
}
