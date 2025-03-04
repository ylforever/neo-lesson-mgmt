package com.neo.lesson.mapper;

import com.neo.lesson.model.UserLogin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户登录信息存取接口
 *
 * @author neo
 * @since 2025-02-28
 */
@Mapper
public interface UserLoginMapper {
    /**
     * 插入登录用户信息
     *
     * @param userLogin 登录用户信息
     */
    void insertLoginUser(@Param("loginUser") UserLogin userLogin);

    /**
     * 更新用户登录信息
     *
     * @param loginUser 登录用户信息
     */
    void updateLoginUser(@Param("loginUser") UserLogin loginUser);

    /**
     * 查询用户的校验码(一个账户只存储一条数据)
     *
     * @param account 用户账号
     * @return 数量
     */
    Integer queryVerifyCode(@Param("account") String account);
}
