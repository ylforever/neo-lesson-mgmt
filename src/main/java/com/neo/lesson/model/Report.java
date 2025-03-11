package com.neo.lesson.model;

import com.elon.base.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 报告模型
 *
 * @author neo
 * @since 2025-03-06
 */
@Getter
@Setter
@ApiModel(value = "报告模型")
public class Report extends BaseModel {
    @ApiModelProperty(value = "自增ID")
    private int id = -1;

    @ApiModelProperty(value = "报告编码")
    private String reportCode = "";

    @ApiModelProperty(value = "文件uuid")
    private String fileId = "";

    @ApiModelProperty(value = "文件名称")
    private String fileName = "";

    @ApiModelProperty(value = "文件相对路径")
    private String FilePath = "";

    @ApiModelProperty(value = "完成状态")
    private String state = "";
}
