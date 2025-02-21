package com.neo.lesson.model;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 学员VO模型
 *
 * @author neo
 * @since 2025-02-19
 */
@Getter
@Setter
@ApiModel(value = "学员VO模型")
public class StudentVO extends Student {
    @ApiModelProperty(value = "课程名称", example = "")
    private String lessonName = "";

    /**
     * 将Student对象转为StudentVO对象
     * @param student 学员对象
     * @return 学员VO模型
     */
    public static StudentVO valueOf(Student student) {
        String json = JSONObject.toJSONString(student);
        StudentVO vo = JSONObject.parseObject(json, StudentVO.class);
        return vo;
    }
}
