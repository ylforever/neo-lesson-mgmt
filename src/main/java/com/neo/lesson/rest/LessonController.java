package com.neo.lesson.rest;

import com.elon.base.model.PageResult;
import com.elon.base.model.PageVO;
import com.elon.base.model.ResultModel;
import com.neo.lesson.model.Lesson;
import com.neo.lesson.service.LessonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 课程管理服务
 *
 * @author neo
 * @since 2025-02-12
 */
@RestController
@RequestMapping("/v1/lesson")
@Api(tags = "课程管理服务")
public class LessonController {
    private static final Logger LOGGER = LogManager.getLogger(LessonController.class);

    @Resource
    private LessonService lessonService;

    /**
     * 插入课程数据
     *
     * @param lesson 课程
     * @return 分配的课程编码
     */
    @PostMapping("/insert-lesson")
    @ApiOperation(value = "插入课程数据")
    @ApiImplicitParam(name = "lesson", value = "课程")
    public ResultModel<String> insertLesson(@RequestBody Lesson lesson) {
        try {
            lessonService.insertLesson(lesson);
            return ResultModel.success(lesson.getLessonCode());
        } catch (Exception e) {
            LOGGER.error("Insert lesson fail. name:{}", lesson.getName() , e);
            return ResultModel.fail("Insert lesson fail.");
        }
    }

    /**
     * 分页查询课程数据
     *
     * @param pageVO 分页条件
     * @return 查询结果
     */
    @PostMapping("/query-lesson-by-page")
    @ApiOperation(value = "分页查询课程数据")
    @ApiImplicitParam(name = "pageVO", value = "分页条件")
    public ResultModel<PageResult<Lesson>> queryLessonByPage(@RequestBody PageVO pageVO) {
        try {
            PageResult<Lesson> lessonPageResult = lessonService.queryLessonByPage(pageVO);
            return ResultModel.success(lessonPageResult);
        } catch (Exception e) {
            LOGGER.error("Query lesson by page fail. pageVO:{}", pageVO.toString(), e);
            return ResultModel.fail("Query lesson by page fail");
        }
    }

    /**
     * 删除课程数据
     *
     * @param lessonCode 课程编码
     * @return 处理结果
     */
    @DeleteMapping("/delete-lesson/{lessonCode}")
    @ApiOperation(value = "删除课程")
    public ResultModel<String> deleteLesson(@PathVariable("lessonCode") String lessonCode){
        try {
            lessonService.deleteLesson(lessonCode);
            return ResultModel.success(lessonCode);
        } catch (Exception e) {
            LOGGER.error("Delete lesson fail. lessCode:{}", lessonCode, e);
            return ResultModel.fail("Delete lesson fail.");
        }
    }
}
