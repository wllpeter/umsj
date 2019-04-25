package com.tuniu.bi.umsj.base.entitydo;

import java.util.List;

/**
 * @author zhangwei21
 */
public class SmsResponseDO extends AbstractResponseDO{

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private Integer successNum;

        private Integer failNum;

        private List<Detail> details;

        public Integer getSuccessNum() {
            return successNum;
        }

        public void setSuccessNum(Integer successNum) {
            this.successNum = successNum;
        }

        public Integer getFailNum() {
            return failNum;
        }

        public void setFailNum(Integer failNum) {
            this.failNum = failNum;
        }

        public List<Detail> getDetails() {
            return details;
        }

        public void setDetails(List<Detail> details) {
            this.details = details;
        }

        public static class Detail {
            private String mobileNum;

            private Boolean success;

            private Integer errorCode;

            private String message;

            public String getMobileNum() {
                return mobileNum;
            }

            public void setMobileNum(String mobileNum) {
                this.mobileNum = mobileNum;
            }

            public Boolean getSuccess() {
                return success;
            }

            public void setSuccess(Boolean success) {
                this.success = success;
            }

            public Integer getErrorCode() {
                return errorCode;
            }

            public void setErrorCode(Integer errorCode) {
                this.errorCode = errorCode;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }
        }
    }

    @Override
    public String toString() {
        return "SmsResponseDO{" +
                "data=" + data +
                '}';
    }
}
