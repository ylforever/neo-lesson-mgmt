package com.neo.lesson.service;

import com.elon.base.constant.EnumThreadTaskType;
import com.elon.base.constant.NeoConst;
import com.elon.base.service.thread.ThreadPoolUtils;
import com.elon.base.util.DateUtil;
import com.elon.base.util.FileUtil;
import com.elon.base.util.StringUtil;
import com.neo.lesson.constant.LessonMgMtConst;
import com.neo.lesson.manage.GenerateReportExecutor;
import com.neo.lesson.mapper.LessonMapper;
import com.neo.lesson.mapper.ReportMapper;
import com.neo.lesson.mapper.StudentMapper;
import com.neo.lesson.model.Report;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 报告服务
 *
 * @author neo
 * @since 2025-03-06
 */
@Component
public class ReportService {
    private static final Logger LOGGER = LogManager.getLogger(ReportService.class);

    @Resource
    private ReportMapper reportMapper;

    @Resource
    private LessonMapper lessonMapper;

    @Resource
    private StudentMapper studentMapper;

    private ThreadPoolUtils threadPoolUtils = new ThreadPoolUtils(2, 10);

    /**
     * 生成报告
     *
     * @param account 用户账号
     * @author neo
     * @return 报告编码
     * @since 2025/3/7
     */
    public String generateReport(String account){
        try {
            // 1、插入报告记录
            Report report = insertReport(account);

            // 2、异步生成报告文件
            GenerateReportExecutor generateReportExecutor = new GenerateReportExecutor(EnumThreadTaskType.GENERATE_REPORT,
                    lessonMapper, studentMapper, reportMapper, report);
            threadPoolUtils.executeTask(generateReportExecutor);
            return report.getReportCode();
        } catch (Exception e){
            LOGGER.error("Generate report fail.", e);
            return "";
        }
    }

    /** 
     * 查询报告列表
     *
     * @param account 报告创建人账号
     * @return 报告列表
     * @author neo
     * @since 2025/3/7
     */ 
    public List<Report> queryLessonReport(String account){
        return reportMapper.getLessonReport(account);
    }

    /**
     * 下载报告
     *
     * @param reportCode 报告编码
     * @param response http响应
     */
    public void downloadReport(String reportCode, HttpServletResponse response) {
        Report report = reportMapper.getReportByCode(reportCode);
        String fileLocalPath = LessonMgMtConst.WORK_PATH + report.getFilePath();

        LOGGER.info("Download file path:{}", fileLocalPath);
        FileUtil.downloadLocalFile(fileLocalPath, report.getFileName() + NeoConst.EXCEL_SUFFIX, response);
    }

    /**
     * 删除报告
     *
     * @param reportCode 报告编码
     * @author neo
     * @since 2025/3/11
     */
    public void deleteReport(String reportCode){
        Report report = reportMapper.getReportByCode(reportCode);
        String fileLocalPath = LessonMgMtConst.WORK_PATH + report.getFilePath();

        // 1、删除报告数据
        reportMapper.deleteReport(reportCode);

        // 2、删除报告文件
        FileUtil.deleteFile(fileLocalPath);
    }

    /**
     * 插入一条数据到报告表
     *
     * @param account 账号
     * @return 报告对象
     * @author neo
     * @since 2025/3/7
     */
    private Report insertReport(String account){
        Report report = new Report();

        String reportFileId = StringUtil.generateUuid();
        report.setFileId(reportFileId);
        report.setFilePath("/report/" + reportFileId + NeoConst.EXCEL_SUFFIX);
        report.setFileName("课程管理_" + DateUtil.toLocalDateStr(new Date()));
        report.setReportCode(StringUtil.generateUuid());
        report.setCreateUser(account);
        report.setCreateTime(new Date());
        report.setState("生成中");

        reportMapper.insertReport(report);
        return report;
    }
}
