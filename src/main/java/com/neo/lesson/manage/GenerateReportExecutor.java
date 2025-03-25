package com.neo.lesson.manage;

import com.elon.base.constant.EnumThreadTaskType;
import com.elon.base.constant.NeoConst;
import com.elon.base.model.ThreadTaskBase;
import com.elon.base.service.excel.ExcelWriterUtil;
import com.elon.base.util.StringUtil;
import com.neo.lesson.constant.LessonMgMtConst;
import com.neo.lesson.mapper.LessonMapper;
import com.neo.lesson.mapper.ReportMapper;
import com.neo.lesson.mapper.StudentMapper;
import com.neo.lesson.model.Lesson;
import com.neo.lesson.model.Report;
import com.neo.lesson.model.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

/**
 * 生成报告异步任务执行
 *
 * @author neo
 * @since 2025/3/7
 * @version 1.0
 */
public class GenerateReportExecutor extends ThreadTaskBase {
    private static Logger LOGGER = LogManager.getLogger(GenerateReportExecutor.class);

    private final LessonMapper lessonMapper;

    private final StudentMapper studentMapper;

    private final ReportMapper reportMapper;

    private final Report report;

    public GenerateReportExecutor(EnumThreadTaskType taskType, LessonMapper lessonMapper,
                                  StudentMapper studentMapper, ReportMapper reportMapper,
                                  Report report) {
        super(StringUtil.generateUuid(), taskType);

        this.lessonMapper = lessonMapper;
        this.studentMapper = studentMapper;
        this.reportMapper = reportMapper;
        this.report = report;
    }

    @Override
    public void run() {
        try {
            // 1、复制一个报告模板
            String fileId =  report.getFileId();
            String templatePath = LessonMgMtConst.WORK_PATH + "/template/lesson_mgmt.xlsx";
            String reportFilePath = LessonMgMtConst.WORK_PATH + "/report/" + fileId + NeoConst.EXCEL_SUFFIX;
            Files.copy(Paths.get(templatePath), Paths.get(reportFilePath), StandardCopyOption.REPLACE_EXISTING);

            // 2、获取课时报表数据
            List<List<String>> excelDataList = getReportDataList();

            // 3、写入数据到Excel
            ExcelWriterUtil writer = new ExcelWriterUtil(reportFilePath);
            writer.writeData(0, 1, excelDataList);

            // 4、更新报告状态
            reportMapper.updateReportState(report.getReportCode(), "已生成");
        } catch (Exception e) {
            LOGGER.error("Generate report fail.", e);
        }
    }

    /** 
     * 获取报告数据列表
     *
     * @return 数据列表
     * @author neo
     * @since 2025/3/7
     */ 
    private List<List<String>> getReportDataList() {
        List<Lesson> lessonList = lessonMapper.queryLessonList(report.getCreateUser());
        List<List<String>> excelDataList = new ArrayList<>();

        // 获取每个课程下的学员数据
        for (Lesson lesson : lessonList) {
            List<Student> studentList = studentMapper.queryStudentByPage(50, 0, lesson.getLessonCode());

            // 每一个学员生成一条数据
            for (Student student : studentList) {
                List<String> rowDataList = new ArrayList<>();

                // 学员名称
                rowDataList.add(student.getName());

                // 课程名称
                rowDataList.add(lesson.getName());

                // 学员剩余课时
                rowDataList.add(String.valueOf(student.getSurplusLessonNum()));

                // 学员电子邮箱
                rowDataList.add(student.getEmail());

                // 指导老师
                rowDataList.add(lesson.getDirector());

                excelDataList.add(rowDataList);
            }
        }

        return excelDataList;
    }
}
