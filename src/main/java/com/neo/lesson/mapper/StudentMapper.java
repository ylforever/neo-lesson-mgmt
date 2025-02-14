package com.neo.lesson.mapper;

import com.neo.lesson.model.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 查询课程包含的总的学员数量
     *
     * @return 学员数量
     */
    int queryStudentAmount(@Param("lessonCode") String lessonCode);

    /**
     * 查询学员数据
     *
     * @param lessonCode 课程编码
     * @param studentCode 学员编码
     * @return 学员信息
     */
    Student queryStudent(@Param("lessonCode") String lessonCode, @Param("studentCode") String studentCode);

    /**
     * 分页查询学员数量
     *
     * @param amount 查询数量
     * @param offSet 查询偏移数量
     * @param lessCode 课程编码
     * @return 插叙结果列表
     */
    List<Student> queryStudentByPage(@Param("amount") int amount, @Param("offSet") int offSet,
                                     @Param("lessCode") String lessCode);

    /**
     * 删除课程下的学员数据。
     *
     * @param lessonCode 课程编码
     * @param studentCode 学员编码
     */
    void deleteStudent(@Param("lessonCode") String lessonCode, @Param("studentCode") String studentCode);

    /**
     * 更新剩余课时数量
     *
     * @param lessonCode 课程编码
     * @param studentCode 学员编码
     * @param lessonAmount 课时数量
     */
    void updateSurplusLessonNum(@Param("lessonCode") String lessonCode, @Param("studentCode") String studentCode,
                                @Param("lessonAmount") int lessonAmount);
}
