package com.neo.lesson.model;

import com.elon.base.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

/**
 * 课时学员关系对象模型
 *
 * @author neo
 * @since 2025-02-12
 */
@Getter
@Setter
public class LessonStudentRelation extends BaseModel {
    /**
     * 自增ID
     */
    private int id = -1;

    /**
     * 课程唯一编码. uuid
     */
    private String lessonCode = "";

    /**
     * 学员唯一编码。 uuid
     */
    private String studentCode = "";
}
