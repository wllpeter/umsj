package com.tuniu.bi.umsj.base.vo;

import javax.validation.constraints.NotBlank;

public class CreateUdsPublishItemVO {

    @NotBlank(message = "代码类型不能为空")
    private String codeType;

    @NotBlank(message = "代码路径不能为空")
    private String codePath;

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getCodePath() {
        return codePath;
    }

    public void setCodePath(String codePath) {
        this.codePath = codePath;
    }
}
