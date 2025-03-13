package com.neo.lesson.service;

import com.elon.base.model.PageResult;
import com.elon.base.model.PageVO;
import com.elon.base.util.StringUtil;
import com.neo.lesson.constant.LessonMgMtConst;
import com.neo.lesson.mapper.LessonMapper;
import com.neo.lesson.mapper.StudentMapper;
import com.neo.lesson.model.Email;
import com.neo.lesson.model.Student;
import com.neo.lesson.model.StudentVO;
import com.neo.lesson.util.EmailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 学员管理服务类
 *
 * @author neo
 * @since 2025-02-14
 */
@Component
public class StudentService {
    private static final Logger LOGGER = LogManager.getLogger(StudentService.class);

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private LessonMapper lessonMapper;

    @Resource
    private EmailService emailService;

    /**
     * 插入学员数据到数据库
     *
     * @param student 学员信息
     * @author neo
     * @since 2025-02-12
     */
    public void insertStudent(Student student) {
        student.setStudentCode(StringUtil.generateUuid());
        student.setCreateUser("neo");
        student.setCreateTime(new Date());
        studentMapper.insertStudent(student);
    }

    /**
     * 分页查询学员数据
     *
     * @param pageVO 分页条件
     * @param lessonCode 课程编码
     * @param account 用户账号
     * @return 查询结果
     */
    public PageResult<StudentVO> queryStudentByPage(PageVO pageVO, String lessonCode, String account) {
        // 1、查询总学员数量
        int totalAmount = studentMapper.queryStudentAmount(lessonCode);

        // 2、分页插叙学员
        int offset = (pageVO.getPageNo() - 1) * pageVO.getAmount();
        List<Student> studentList = studentMapper.queryStudentByPage(pageVO.getAmount(), offset, lessonCode);

        // 3、查询课程编码与名称的对应关系
        String lessonName = lessonMapper.queryLessonName(lessonCode);

        List<StudentVO> voList = new ArrayList<>();
        studentList.forEach(student -> {
            StudentVO vo = StudentVO.valueOf(student);
            vo.setLessonName(lessonName);
            voList.add(vo);
        });

        return PageResult.create(totalAmount, voList);
    }

    /**
     * 删除课程下的学员数据。
     *
     * @param lessonCode 课程编码
     * @param studentCode 学员编码
     */
    public void deleteStudent(String lessonCode, String studentCode) {
        studentMapper.deleteStudent(lessonCode, studentCode);
    }

    /**
     * 扣减课时
     *
     * @param lessonCode 课程编码
     * @param studentCode 学员编码
     * @param lessonAmount 扣减课时数量
     * @return 旧课时数量
     */
    public int decreaseLessonNum(String lessonCode, String studentCode, int lessonAmount, String account) {
        // 1、查询当前课时数量
        Student student = studentMapper.queryStudent(lessonCode, studentCode);
        if (student == null) {
            LOGGER.error("The student is not exist. lessonCode:{}|studentCode:{}", lessonCode, studentCode);
            return -1;
        }

        int oldLessonNum = student.getSurplusLessonNum();
        studentMapper.updateSurplusLessonNum(lessonCode, studentCode, oldLessonNum - lessonAmount, account, new Date());

        // 2、发送邮件通知
        Email email = buildUpdateLessonNumEmail(student, lessonAmount, oldLessonNum - lessonAmount, false);
        emailService.sendMail(email);

        return oldLessonNum;
    }

    /**
     * 增加课时
     *
     * @param lessonCode 课程编码
     * @param studentCode 学员编码
     * @param lessonAmount 扣减课时数量
     * @return 旧课时数量
     */
    public int increaseLessonNum(String lessonCode, String studentCode, int lessonAmount, String account) {
        // 查询当前课时数量
        Student student = studentMapper.queryStudent(lessonCode, studentCode);
        if (student == null) {
            LOGGER.error("The student is not exist. lessonCode:{}|studentCode:{}", lessonCode, studentCode);
            return -1;
        }

        int oldLessonNum = student.getSurplusLessonNum();
        studentMapper.updateSurplusLessonNum(lessonCode, studentCode, oldLessonNum + lessonAmount, account, new Date());

        // 发送通知邮件
        Email email = buildUpdateLessonNumEmail(student, lessonAmount, oldLessonNum + lessonAmount, true);
        emailService.sendMail(email);

        return oldLessonNum;
    }

    /**
     * 构建更新课时通知邮件.
     *
     * @param student 学员信息
     * @param updateLessonNum 更新课时数量
     * @param surplusLessonNum 剩余课时数量
     * @param operate true-增加课时, false-扣减课时
     * @return email对象
     */
    private Email buildUpdateLessonNumEmail(Student student, int updateLessonNum, int surplusLessonNum,
                                            boolean operate) {
        String operateName = operate ? "增加课时" : "扣减课时";

        // 查询课时名称
        String lessonName = lessonMapper.queryLessonName(student.getLessonCode());

        Email email = new Email();
        email.getReceiverList().add(student.getEmail());

        // 构建邮件标题
        StringBuilder titleBuilder = new StringBuilder();
        titleBuilder.append("[通知]")
                    .append(" 课程名称:").append(lessonName)
                    .append("  ").append(operateName).append(":").append(updateLessonNum)
                    .append("  剩余课时:").append(surplusLessonNum);

        email.setTitle(titleBuilder.toString());

        // 填充邮件内容
        String contentFormat = String.format(LessonMgMtConst.context, operateName, lessonName, student.getName(),
                updateLessonNum, surplusLessonNum);
        email.setContent(contentFormat);
        return email;
    }
}
