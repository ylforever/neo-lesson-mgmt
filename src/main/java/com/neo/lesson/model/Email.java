package com.neo.lesson.model;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 发送邮件数据模型定义
 *
 * @author neo
 * @since 2025-02-22
 */
@Getter
@Setter
public class Email {
    /**
     * 邮件标题
     */
    private String title = "";

    /**
     * 邮件内容
     */
    private String content = "";

    /**
     * 收件人列表
     */
    private List<String> receiverList = new ArrayList<>();

    /**
     * 密送人列表
     */
    private List<String> bccList = new ArrayList<>();

    /**
     * 邮件付件列表
     */
    private List<File> attachFileList = new ArrayList<>();
}
