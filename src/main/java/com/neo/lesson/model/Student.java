package com.neo.lesson.model;

import com.elon.base.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

/**
 * 学员对象模型
 *
 * @author neo
 * @since 2025-02-12
 */
@Getter
@Setter
public class Student extends BaseModel {
    /**
     * 自增ID
     */
    private int id = -1;

    /**
     * 学员唯一编码。 uuid
     */
    private String studentCode = "";

    /**
     * 学员名称
     */
    private String name = "";

    /**
     * 学员email。 用于发送通知。
     */
    private String email = "";

    /**
     * 剩余课数数量. 允许为负数(欠缴课时费)
     */
    private int surplusLessonNum = 0;
}
