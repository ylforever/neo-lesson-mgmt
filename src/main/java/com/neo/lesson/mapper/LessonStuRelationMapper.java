package com.neo.lesson.mapper;

import com.neo.lesson.model.LessonStudentRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 课程学员关系数据访问接口
 *
 * @author neo
 * @since 2025-02-12
 */
@Mapper
public interface LessonStuRelationMapper {
    /**
     * 插入课程学员关系数据到数据库
     *
     * @param relation 关系数据
     * @author neo
     * @since 2025-02-12
     */
    void insertLessonStuRelation(@Param("relation") LessonStudentRelation relation);
}
