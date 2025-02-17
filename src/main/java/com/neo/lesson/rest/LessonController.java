package com.neo.lesson.rest;

import com.elon.base.model.PageResult;
import com.elon.base.model.PageVO;
import com.elon.base.model.ResultModel;
import com.elon.base.rest.BaseController;
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
import java.util.List;

/**
 * 课程管理服务
 *
 * @author neo
 * @since 2025-02-12
 */
@RestController
@RequestMapping("/v1/lesson")
@Api(tags = "课程管理服务")
public class LessonController extends BaseController {
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
     * 查询课程列表
     *
     * @return 查询结果
     */
    @PostMapping("/query-lesson-list")
    @ApiOperation(value = "课程列表")
    public ResultModel<List<Lesson>> queryLessonList() {
        try {
            String account = getUserAccount();
            List<Lesson> lessonList = lessonService.queryLessonList(account);
            return ResultModel.success(lessonList);
        } catch (Exception e) {
            LOGGER.error("Query lesson by page fail.", e);
            return ResultModel.fail("Query lesson list fail");
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
