package com.neo.lesson.service;

import com.elon.base.model.MapQueryHelper;
import com.elon.base.util.StringUtil;
import com.neo.lesson.mapper.LessonMapper;
import com.neo.lesson.mapper.StudentMapper;
import com.neo.lesson.model.Lesson;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<Lesson>  lessonList =  lessonMapper.queryLessonList(userAccount);
        if (lessonList.isEmpty()) {
            return lessonList;
        }

        // 汇总剩余课时
        List<String> lessonCodeList = new ArrayList<>();
        lessonList.forEach((lesson->lessonCodeList.add(lesson.getLessonCode())));

        List<MapQueryHelper<String, Long>> resultList = studentMapper.queryLessonTotalNum(lessonCodeList);
        Map<String, Long> resultMap = new HashMap<>();
        resultList.forEach((result)->resultMap.put(result.getKey(), result.getValue()));

        for (Lesson lesson : lessonList) {
            if (resultMap.containsKey(lesson.getLessonCode())) {
                int totalLessonNum = Math.toIntExact(resultMap.get(lesson.getLessonCode()));
                lesson.setTotalLessonNum(totalLessonNum);
            }
        }

        return lessonList;
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
