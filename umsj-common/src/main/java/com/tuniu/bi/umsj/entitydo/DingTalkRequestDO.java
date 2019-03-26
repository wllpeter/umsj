package com.tuniu.bi.umsj.entitydo;

import com.tuniu.bi.umsj.vo.DingTalkRequestVO;

/**
 * 钉钉请求类
 * @author zhangwei21
 */
public class DingTalkRequestDO extends DingTalkRequestVO {

    private Integer stepId;

    public Integer getStepId() {
        return stepId;
    }

    public void setStepId(Integer stepId) {
        this.stepId = stepId;
    }
}
