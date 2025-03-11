package com.neo.lesson.mapper;

import com.neo.lesson.model.Report;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 报告数据存取接口
 *
 * @author neo
 * @since 2025/3/6
 * @version 1.0
 */
@Mapper
public interface ReportMapper {
    /** 
     * 插入报告数据
     *
     * @param report 报告对象
     * @return 
     * @author neo
     * @since 2025/3/7
     */ 
    void insertReport(@Param("report") Report report);
    
    /** 
     * 更新报告状态
     *
     * @param reportCode 报告编码
     * @param state 状态
     * @return 
     * @author neo
     * @since 2025/3/7
     */ 
    void updateReportState(@Param("reportCode") String reportCode, @Param("state") String state);
    
    /**
     * 获取报告对象
     *
     * @param reportCode 报告编码
     * @return 报告对象
     * @author neo
     * @since 2025/3/6
     */
    Report getReportByCode(@Param("reportCode") String reportCode);

    /** 
     * 获取课程报告列表
     *
     * @param account 报告创建人账号
     * @return 报告列表
     * @author neo
     * @since 2025/3/7
     */ 
    List<Report> getLessonReport(@Param("account") String account);
}
