package com.neo.lesson.model;

import com.elon.base.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户权限
 */
@ApiModel(value = "用户权限")
@Getter
@Setter
public class UserPrivilege extends BaseModel {
    @ApiModelProperty(value = "用户账号(email)")
    private String account = "";

    @ApiModelProperty(value = "课程编码")
    private String lessonCode = "";

    @ApiModelProperty(value = "权限(write or read)")
    private String privilege = "";
}
