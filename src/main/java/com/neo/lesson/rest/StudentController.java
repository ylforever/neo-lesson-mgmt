package com.neo.lesson.rest;

import com.elon.base.model.PageResult;
import com.elon.base.model.PageVO;
import com.elon.base.model.ResultModel;
import com.neo.lesson.model.Student;
import com.neo.lesson.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
 * 学员管理服务
 *
 * @author neo
 * @since 2025-02-14
 */
@RestController
@RequestMapping("/v1/student")
@Api(tags = "学员管理服务")
public class StudentController {
    private static final Logger LOGGER = LogManager.getLogger(StudentController.class);

    @Resource
    private StudentService studentService;

    /**
     * 插入学员数据到数据库
     *
     * @param student 学员信息
     * @return 分配的学员编码
     * @author neo
     * @since 2025-02-12
     */
    @PostMapping("/insert-student")
    @ApiOperation(value = "插入学员数据到数据库")
    @ApiImplicitParam(name = "student", value = "学员信息")
    public ResultModel<String> insertStudent(@RequestBody Student student) {
        try {
            studentService.insertStudent(student);
            return ResultModel.success(student.getStudentCode());
        } catch (Exception e) {
            LOGGER.error("Insert student fail. name:{}", student.getName(), e);
            return ResultModel.fail("Insert student fail.");
        }
    }

    /**
     * 分页查询学员数据
     *
     * @param pageVO 分页条件
     * @param lessonCode 课程编码
     * @return 查询结果
     */
    @PostMapping("/query-student-by-page/{lessonCode}")
    @ApiOperation(value = "分页查询学员数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageVO", value = "分页条件"),
            @ApiImplicitParam(name = "lessonCode", value = "课程编码")
    })
    public ResultModel<PageResult<Student>> queryStudentByPage(@RequestBody PageVO pageVO,
                                                               @PathVariable("lessonCode") String lessonCode) {
        try {
            PageResult<Student> pageResult = studentService.queryStudentByPage(pageVO, lessonCode);
            return ResultModel.success(pageResult);
        } catch (Exception e) {
            LOGGER.error("Query student by page fail. lessonCode:{}|pageVO:{}", lessonCode, pageVO.toString(), e);
            return ResultModel.fail("Query student by page fail.");
        }
    }

    /**
     * 删除课程下的学员数据。
     *
     * @param lessonCode 课程编码
     * @param studentCode 学员编码
     */
    @DeleteMapping("/delete-student/{lessonCode}/{studentCode}")
    @ApiOperation(value = "删除课程下的学员数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lessonCode", value = "课程编码"),
            @ApiImplicitParam(name = "studentCode", value = "学员编码")
    })
    public ResultModel<String> deleteStudent(@PathVariable("lessonCode") String lessonCode,
                                             @PathVariable("studentCode") String studentCode) {
        try {
            studentService.deleteStudent(lessonCode, studentCode);
            return ResultModel.success(studentCode);
        } catch (Exception e) {
            LOGGER.error("Delete student fail. lessonCode:{}|studentCode:{}", lessonCode, studentCode, e);
            return ResultModel.fail("Delete student fail.");
        }
    }

    /**
     * 更新剩余课时数量
     *
     * @param lessonCode 课程编码
     * @param studentCode 学员编码
     * @param lessonAmount 课时数量
     * @return 更新前的课时数
     */
    @PostMapping("/update-surplus-lesson-num/{lessonCode}/{studentCode}/{lessonAmount}")
    @ApiOperation(value = "更新剩余课时数量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lessonCode", value = "课程编码"),
            @ApiImplicitParam(name = "studentCode", value = "学员编码"),
            @ApiImplicitParam(name = "lessonAmount", value = "课时数量")
    })
    public ResultModel<Integer> updateSurplusLessonNum(@PathVariable("lessonCode") String lessonCode,
                                             @PathVariable("studentCode") String studentCode,
                                             @PathVariable("lessonAmount") int lessonAmount) {
        try {
            int oldLessonNum = studentService.updateSurplusLessonNum(lessonCode, studentCode, lessonAmount);
            return ResultModel.success(oldLessonNum) ;
        } catch (Exception e) {
            LOGGER.error("Update surplus lesson num fail. lessonCode:{}|studentCode:{}|lessonAmount:{}",
                    lessonCode, studentCode, lessonAmount, e);
            return ResultModel.fail("Update surplus lesson num fail.");
        }
    }


}
