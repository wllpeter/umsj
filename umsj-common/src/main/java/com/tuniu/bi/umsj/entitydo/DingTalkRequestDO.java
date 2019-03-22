package com.tuniu.bi.umsj.entitydo;

import com.tuniu.bi.umsj.vo.DingTalkReqeustVO;

/**
 * 钉钉请求类
 * @author zhangwei21
 */
public class DingTalkRequestDO extends DingTalkReqeustVO {

    private Integer stepId;

    public Integer getStepId() {
        return stepId;
    }

    public void setStepId(Integer stepId) {
        this.stepId = stepId;
    }
}
