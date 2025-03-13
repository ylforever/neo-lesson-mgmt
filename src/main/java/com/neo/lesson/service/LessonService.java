package com.neo.lesson.service;

import com.elon.base.util.StringUtil;
import com.neo.lesson.mapper.LessonMapper;
import com.neo.lesson.mapper.StudentMapper;
import com.neo.lesson.model.Lesson;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 课程管理服务类
 *
 * @author neo
 * @since 2025-02-12
 */
@Component
public class LessonService {
    @Resource
    private LessonMapper lessonMapper;

    @Resource
    private StudentMapper studentMapper;

    /**
     * 插入课程数据到数据库
     *
     * @param lesson 课程
     * @param account 创建人账号
     * @return 分配的自增ID
     */
    public int insertLesson(Lesson lesson, String account) {
        lesson.setLessonCode(StringUtil.generateUuid());
        lesson.setCreateUser(account);
        lesson.setCreateTime(new Date());
        lessonMapper.insertLesson(lesson);
        return lesson.getId();
    }

    /**
     * 分页查询课程数据
     *
     * @param userAccount 用户账号
     * @return 查询结果
     */
    public List<Lesson> queryLessonList(String userAccount) {
        return lessonMapper.queryLessonList(userAccount);
    }

    /**
     * 删除课程
     *
     * @param lessonCode 课程编码
     */
    public void deleteLesson(String lessonCode) {
        studentMapper.deleteStudent(lessonCode, null);
        lessonMapper.deleteLesson(lessonCode);
    }
}
