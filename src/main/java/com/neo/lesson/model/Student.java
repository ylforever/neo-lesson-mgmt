package com.neo.lesson.model;

import com.elon.base.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 学员对象模型. 同一个学员参加了多个课程，用多条数据记录.
 *
 * @author neo
 * @since 2025-02-12
 */
@Getter
@Setter
@ApiModel(value = "学员对象模型")
public class Student extends BaseModel {
    @ApiModelProperty(value = "自增ID")
    private int id = -1;

    @ApiModelProperty(value = "学员唯一编码. uuid")
    private String studentCode = "";

    @ApiModelProperty(value = "学员参加的课程编码")
    private String lessonCode = "";

    @ApiModelProperty(value = "学员名称")
    private String name = "";

    @ApiModelProperty(value = "学员email。 用于发送通知")
    private String email = "";

    @ApiModelProperty(value = "剩余课数数量. 允许为负数(表示欠缴课时费)")
    private int surplusLessonNum = 0;

    @ApiModelProperty(value = "备注")
    private String remark = "";
}
