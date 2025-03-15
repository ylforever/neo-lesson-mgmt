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
    @ApiModelProperty(value = "自增ID")
    private int id = -1;

    @ApiModelProperty(value = "课程唯一编码. uuid")
    private String lessonCode = "";

    @ApiModelProperty(value = "课程名称")
    private String name = "";

    @ApiModelProperty(value = "备注")
    private String remark = "";

    @ApiModelProperty(value = "指导老师")
    private String director = "";

    @ApiModelProperty(value = "总剩余课时")
    private int totalLessonNum = 0;
}
