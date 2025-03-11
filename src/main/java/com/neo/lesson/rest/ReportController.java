package com.neo.lesson.rest;

import com.elon.base.model.ResultModel;
import com.elon.base.rest.BaseController;
import com.neo.lesson.model.Report;
import com.neo.lesson.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 报告管理服务
 *
 * @author neo
 * @since 2025-03-06
 */
@RestController
@RequestMapping("/v1/report")
@Api(tags = "报告管理服务")
public class ReportController extends BaseController {
    private static final Logger LOGGER = LogManager.getLogger(ReportController.class);

    @Resource
    private ReportService reportService;

    /**
     * 查询课程报告列表
     *
     * @return 报告列表
     * @author neo
     * @since 2025/3/7
     */
    @GetMapping("/query-lesson-report")
    @ApiOperation(value = "查询课程报告列表")
    public ResultModel<List<Report>> queryLessonReport(){
        String account = getUserAccount();
        try {
            LOGGER.info("Invoke queryLessonReport begin. account:{}", account);
            List<Report> reportList = reportService.queryLessonReport(account);
            LOGGER.info("Invoke queryLessonReport end. account:{}|report size:{}", account, reportList.size());
            return ResultModel.success(reportList);
        } catch (Exception e) {
            LOGGER.error("Invoke queryLessonReport fail. account:{}", account);
            return ResultModel.fail("Query report fail.");
        }
    }

    /**
     * 生成报告
     *
     * @return 处理结果
     */
    @PostMapping("/generate-report")
    @ApiOperation(value = "生成报告")
    public ResultModel<String> generateReport(){
        String account = getUserAccount();
        try {
            LOGGER.info("Invoke generateReport begin. account:{}", account);
            String reportCode = reportService.generateReport(account);

            LOGGER.info("Invoke generateReport end. account:{}", account);
            return ResultModel.success(reportCode);
        } catch (Exception e) {
            LOGGER.error("Invoke generateReport fail. account:{}", account);
            return ResultModel.fail("Generate report fail.");
        }
    }

    /**
     * 下载报告
     *
     * @param reportCode 报告编码
     * @param response http响应
     */
    @GetMapping("/download-report/{reportCode}")
    public void downloadReport(@PathVariable("reportCode") String reportCode, HttpServletResponse response) {
        try {
            LOGGER.info("Invoke downloadReport begin. reportCode:{}", reportCode);
            reportService.downloadReport(reportCode, response);
            LOGGER.info("Invoke downloadReport end. reportCode:{}", reportCode);
        }catch (Exception e) {
            LOGGER.error("Invoke downloadReport fail. reportCode:{}", reportCode, e);
        }
    }
}
