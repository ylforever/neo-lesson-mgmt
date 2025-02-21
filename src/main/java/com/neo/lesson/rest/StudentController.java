package com.neo.lesson.rest;

import com.elon.base.model.PageResult;
import com.elon.base.model.PageVO;
import com.elon.base.model.ResultModel;
import com.elon.base.rest.BaseController;
import com.neo.lesson.model.Student;
import com.neo.lesson.model.StudentVO;
import com.neo.lesson.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin
@RestController
@RequestMapping("/v1/student")
@Api(tags = "学员管理服务")
public class StudentController extends BaseController {
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
            LOGGER.info("Invoke insertStudent begin. student:{}", student.toString());
            studentService.insertStudent(student);
            LOGGER.info("Invoke insertStudent end. student code:{}", student.getStudentCode());
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
    public ResultModel<PageResult<StudentVO>> queryStudentByPage(@RequestBody PageVO pageVO,
                                                                 @PathVariable("lessonCode") String lessonCode) {
        try {
            String account = getUserAccount();
            LOGGER.info("Invoke queryStudentByPage begin. lessonCode:{}|account:{}|pageVO:{}",
                    lessonCode, account, pageVO.toString());

            PageResult<StudentVO> pageResult = studentService.queryStudentByPage(pageVO, lessonCode, account);

            LOGGER.info("Invoke queryStudentByPage end. lessonCode:{}|account:{}|pageVO:{}|voList size:{}",
                    lessonCode, account, pageVO.toString(), pageResult.getDataList().size());
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
            LOGGER.info("Invoke deleteStudent begin. lessonCode:{}|studentCode:{}", lessonCode, studentCode);
            studentService.deleteStudent(lessonCode, studentCode);
            LOGGER.info("Invoke deleteStudent end. lessonCode:{}|studentCode:{}", lessonCode, studentCode);
            return ResultModel.success(studentCode);
        } catch (Exception e) {
            LOGGER.error("Delete student fail. lessonCode:{}|studentCode:{}", lessonCode, studentCode, e);
            return ResultModel.fail("Delete student fail.");
        }
    }

    /**
     * 扣减课时
     *
     * @param lessonCode 课程编码
     * @param studentCode 学员编码
     * @param lessonAmount 课时数量
     * @return 更新前的课时数
     */
    @PostMapping("/decrease-lesson-num/{lessonCode}/{studentCode}/{lessonAmount}")
    @ApiOperation(value = "扣减课时")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lessonCode", value = "课程编码"),
            @ApiImplicitParam(name = "studentCode", value = "学员编码"),
            @ApiImplicitParam(name = "lessonAmount", value = "课时数量")
    })
    public ResultModel<Integer> decreaseLessonNum(@PathVariable("lessonCode") String lessonCode,
                                             @PathVariable("studentCode") String studentCode,
                                             @PathVariable("lessonAmount") int lessonAmount) {
        try {
            LOGGER.info("Invoke decreaseLessonNum begin. lessonCode:{}|studentCode:{}|lessonAmount:{}",
                    lessonCode, studentCode, lessonAmount);
            int oldLessonNum = studentService.decreaseLessonNum(lessonCode, studentCode, lessonAmount);

            LOGGER.info("Invoke decreaseLessonNum end. oldLessonNum:{}", oldLessonNum);
            return ResultModel.success(oldLessonNum) ;
        } catch (Exception e) {
            LOGGER.error("Decrease lesson num fail. lessonCode:{}|studentCode:{}|lessonAmount:{}",
                    lessonCode, studentCode, lessonAmount, e);
            return ResultModel.fail("Decrease lesson num fail.");
        }
    }

    /**
     * 增加课时
     *
     * @param lessonCode 课程编码
     * @param studentCode 学员编码
     * @param lessonAmount 课时数量
     * @return 更新前的课时数
     */
    @PostMapping("/increase-lesson-num/{lessonCode}/{studentCode}/{lessonAmount}")
    @ApiOperation(value = "增加课时")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lessonCode", value = "课程编码"),
            @ApiImplicitParam(name = "studentCode", value = "学员编码"),
            @ApiImplicitParam(name = "lessonAmount", value = "课时数量")
    })
    public ResultModel<Integer> increaseLessonNum(@PathVariable("lessonCode") String lessonCode,
                                                  @PathVariable("studentCode") String studentCode,
                                                  @PathVariable("lessonAmount") int lessonAmount) {
        try {
            LOGGER.info("Invoke increaseLessonNum begin. lessonCode:{}|studentCode:{}|lessonAmount:{}",
                    lessonCode, studentCode, lessonAmount);
            int oldLessonNum = studentService.increaseLessonNum(lessonCode, studentCode, lessonAmount);

            LOGGER.info("Invoke increaseLessonNum end. oldLessonNum:{}", oldLessonNum);
            return ResultModel.success(oldLessonNum) ;
        } catch (Exception e) {
            LOGGER.error("Increase lesson num fail. lessonCode:{}|studentCode:{}|lessonAmount:{}",
                    lessonCode, studentCode, lessonAmount, e);
            return ResultModel.fail("Increase lesson num fail.");
        }
    }
}
