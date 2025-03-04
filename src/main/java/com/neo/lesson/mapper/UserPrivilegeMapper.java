package com.neo.lesson.mapper;

import com.neo.lesson.model.UserPrivilege;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户权限数据存取接口
 *
 * @author neo
 * @since 2025-02-28
 */
@Mapper
public interface UserPrivilegeMapper {
    /**
     * 插入用户权限数据
     *
     * @param userPrivilege 权限
     */
    void insertUserPrivilege(@Param("userPrivilege") UserPrivilege userPrivilege);

    /**
     * 删除权限数据(在删除课程之后调用)
     *
     * @param lessonCode 课程编码
     */
    void deleteUserPrivilege(@Param("lessonCode") String lessonCode);
}
