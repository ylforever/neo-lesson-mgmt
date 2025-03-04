package com.neo.lesson.model;

import com.alibaba.fastjson.JSONObject;
import com.elon.base.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户登录参数模型
 *
 * @author neo
 * @since 2025-02-28
 */
@Getter
@Setter
@ApiModel(value = "登录用户")
public class UserLogin extends BaseModel {
    @ApiModelProperty(value = "自增ID")
    private int id = -1;

    @ApiModelProperty(value = "账号邮箱")
    private String account = "";

    @ApiModelProperty(value = "验证码")
    private int verifyCode = -1;

    @Override
    public String toString(){
        return JSONObject.toJSONString(this);
    }
}
