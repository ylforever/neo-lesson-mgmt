package com.neo.lesson.service;

import com.elon.base.model.PageResult;
import com.elon.base.model.PageVO;
import com.elon.base.util.StringUtil;
import com.neo.lesson.mapper.LessonMapper;
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

    /**
     * 插入课程数据到数据库
     *
     * @param lesson 课程
     * @return 分配的自增ID
     */
    public int insertLesson(Lesson lesson) {
        lesson.setLessonCode(StringUtil.generateUuid());
        lesson.setCreateUser("neo");
        lesson.setCreateTime(new Date());
        lessonMapper.insertLesson(lesson);
        return lesson.getId();
    }

    /**
     * 分页查询课程数据
     *
     * @param pageVO 分页条件
     * @return 查询结果
     */
    public PageResult<Lesson> queryLessonByPage(PageVO pageVO) {
        // 1、查询总数
        int totalAmount = lessonMapper.queryLessonAmount();

        // 2、查询分页数据
        int offset = (pageVO.getPageNo() - 1) * pageVO.getAmount();
        List<Lesson> lessonList = lessonMapper.queryLessonByPage(pageVO.getAmount(), offset);

        return PageResult.create(totalAmount, lessonList);
    }

    /**
     * 删除课程
     *
     * @param lessonCode 课程编码
     */
    public void deleteLesson(String lessonCode) {
        lessonMapper.deleteLesson(lessonCode);
    }
}
