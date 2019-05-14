package com.tuniu.bi.umsj.base.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author zhangwei21
 */
public class CreateUdsRequestVO {

    @NotBlank(message = "发布单主题不能为空")
    private String title;

    @NotBlank(message = "jira单号不能为空")
    private String jiraId;

    @NotEmpty
    private List<String> codeTypes;

    @NotEmpty
    private List<String> codePaths;

    private String affectedData;

    @NotBlank(message = "ReviewBoard地址不能为空")
    private String reviewBoardUrl;

    private String publishStep;

    private String errRollback;

    private String memo;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJiraId() {
        return jiraId;
    }

    public void setJiraId(String jiraId) {
        this.jiraId = jiraId;
    }

    public List<String> getCodeTypes() {
        return codeTypes;
    }

    public void setCodeTypes(List<String> codeTypes) {
        this.codeTypes = codeTypes;
    }

    public List<String> getCodePaths() {
        return codePaths;
    }

    public void setCodePaths(List<String> codePaths) {
        this.codePaths = codePaths;
    }

    public String getAffectedData() {
        return affectedData;
    }

    public void setAffectedData(String affectedData) {
        this.affectedData = affectedData;
    }

    public String getReviewBoardUrl() {
        return reviewBoardUrl;
    }

    public void setReviewBoardUrl(String reviewBoardUrl) {
        this.reviewBoardUrl = reviewBoardUrl;
    }

    public String getPublishStep() {
        return publishStep;
    }

    public void setPublishStep(String publishStep) {
        this.publishStep = publishStep;
    }

    public String getErrRollback() {
        return errRollback;
    }

    public void setErrRollback(String errRollback) {
        this.errRollback = errRollback;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

}
