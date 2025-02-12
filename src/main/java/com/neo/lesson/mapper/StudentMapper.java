package com.neo.lesson.mapper;

import com.neo.lesson.model.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 学员数据访问接口
 *
 * @author neo
 * @since 2025-02-12
 */
@Mapper
public interface StudentMapper {
    /**
     * 插入学员数据到数据库
     *
     * @param student 学员信息
     * @author neo
     * @since 2025-02-12
     */
    void insertStudent(@Param("student") Student student);
}
