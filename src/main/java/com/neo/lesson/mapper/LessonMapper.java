package com.neo.lesson.mapper;

import com.neo.lesson.model.Lesson;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课程数据访问接口
 *
 * @author neo
 * @since 2025-02-12
 */
@Mapper
public interface LessonMapper {
    /**
     * 插入课程数据到数据库
     *
     * @param lesson 课程对象
     * @return 自增id
     * @author neo
     * @since 2025-02-12
     */
    void insertLesson(@Param("lesson") Lesson lesson);

    /**
     * 查询课程数量
     *
     * @return 课程数量
     */
    int queryLessonAmount();

    /**
     * 分页查询课程数据
     *
     * @param amount 查询数量
     * @param offSet 偏移条数
     * @return 查询结果列表
     */
    List<Lesson> queryLessonByPage(@Param("amount") int amount, @Param("offSet") int offSet);

    /**
     * 删除课程
     *
     * @param lessonCode 课程编码
     */
    void deleteLesson(@Param("lessonCode") String lessonCode);
}
