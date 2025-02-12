package com.neo.lesson.model;

import com.elon.base.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 课程对象模型
 *
 * @author neo
 * @since 2025-02-12
 */
@Getter
@Setter
@ApiModel(value = "课程对象模型")
public class Lesson extends BaseModel {
    @ApiModelProperty(value = "自增ID", example = "-1")
    private int id = -1;

    @ApiModelProperty(value = "课程唯一编码. uuid", example = "")
    private String lessonCode = "";

    @ApiModelProperty(value = "课程名称", example = "")
    private String name = "";

    @ApiModelProperty(value = "备注", example = "")
    private String remark = "";
}
