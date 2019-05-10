package com.tuniu.bi.umsj.base.vo;

import javax.validation.constraints.NotBlank;

public class CreateUdsPublishItemVO {

    @NotBlank(message = "代码类型不能为空")
    private String codeType;

    @NotBlank(message = "代码路径不能为空")
    private String codePath;
}
