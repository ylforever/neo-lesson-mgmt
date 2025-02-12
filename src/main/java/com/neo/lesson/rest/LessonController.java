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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@Api(tags = "课程管理")
public class LessonController {
    private static final Logger LOGGER = LogManager.getLogger(LessonController.class);

    @Resource
    private LessonService lessonService;

    /**
     * 插入课程数据
     *
     * @param lesson 课程
     * @return 分配的自增ID
     */
    @PostMapping("/insert-lesson")
    @ApiOperation(value = "插入课程数据")
    @ApiImplicitParam(name = "lesson", value = "课程")
    public ResultModel<Integer> insertLesson(@RequestBody Lesson lesson) {
        try {
            lessonService.insertLesson(lesson);
            return ResultModel.success(lesson.getId());
        } catch (Exception e) {
            LOGGER.error("Insert lesson fail.", e);
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
    public ResultModel<PageResult<Lesson>> QueryLessonByPage(@RequestBody PageVO pageVO) {
        try {
            PageResult<Lesson> lessonPageResult = lessonService.QueryLessonByPage(pageVO);
            return ResultModel.success(lessonPageResult);
        } catch (Exception e) {
            LOGGER.error("Query lesson by page fail. pageVO:{}", pageVO.toString(), e);
            return ResultModel.fail("Query lesson by page fail");
        }
    }
}
