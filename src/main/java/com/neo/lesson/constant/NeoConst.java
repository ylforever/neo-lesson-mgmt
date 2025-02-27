package com.neo.lesson.constant;

/**
 * 常量定义
 *
 * @author neo
 * @since 2025-02-24
 */
public class NeoConst {
    // 构建邮件html内容
    public static String context = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "    <title>更新课时</title>\n" +
            "\n" +
            "    <style>\n" +
            "        table{\n" +
            "            border: 1px solid;\n" +
            "            border-spacing: 0;\n" +
            "        }\n" +
            "\n" +
            "        table th{\n" +
            "            background-color: #008c8c;\n" +
            "            border: 1px solid;\n" +
            "            border-spacing: 0;\n" +
            "            color: white;\n" +
            "            width: 100px;\n" +
            "        }\n" +
            "\n" +
            "        table td{\n" +
            "            border: 1px solid;\n" +
            "            border-spacing: 0;\n" +
            "            text-align: center;\n" +
            "        }\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "    <table>\n" +
            "        <tr>\n" +
            "            <th>课程名称</th>\n" +
            "            <th>学员姓名</th>\n" +
            "            <th>%s</th>\n" +
            "            <th>剩余课时</th>\n" +
            "            <th>操作员</th>\n" +
            "        </tr>\n" +
            "        <tr>\n" +
            "            <td>%s</td>\n" +
            "            <td>%s</td>\n" +
            "            <td>%d</td>\n" +
            "            <td>%d</td>\n" +
            "            <td>neo</td>\n" +
            "        </tr>\n" +
            "    </table>\n" +
            "</body>\n" +
            "</html>";
}
