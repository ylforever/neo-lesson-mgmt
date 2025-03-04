package com.neo.lesson.service;

import com.elon.base.model.ResultModel;
import com.elon.base.util.JWTUtil;
import com.elon.base.util.StringUtil;
import com.neo.lesson.mapper.UserLoginMapper;
import com.neo.lesson.model.Email;
import com.neo.lesson.model.UserLogin;
import com.neo.lesson.util.EmailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户登录管理
 *
 * @author neo
 * @since 2025-02-28
 */
@Component
public class LoginService {
    private static final Logger LOGGER = LogManager.getLogger(LoginService.class);

    @Resource
    private EmailService emailService;

    @Resource
    private UserLoginMapper userLoginMapper;

    /**
     * 用户登录
     *
     * @param loginUser 用户信息
     * @return 生成的token
     */
    public ResultModel<String> login(UserLogin loginUser) {
        // 1、用户存储的校验码
        Integer verifyCode = userLoginMapper.queryVerifyCode(loginUser.getAccount());
        if (verifyCode == null || loginUser.getVerifyCode() != verifyCode) {
            LOGGER.error("Invalid verify code. verifyCode:{}|loginUser:{}", verifyCode, loginUser);
            return ResultModel.fail("Invalid verify code.");
        }

        // 2、生成JWT
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("account", loginUser.getAccount());
        parameterMap.put("code", String.valueOf(loginUser.getVerifyCode()));

        String token = JWTUtil.instance().createToken(parameterMap);
        return ResultModel.success(token);
    }

    /**
     * 生成校验码
     *
     * @param account 用户账号
     * @return 生成的随机数
     */
    public int generateVerifyCode(String account) {
        // 1、生成6位随机数
        int verifyCode = StringUtil.generateSixDigitRandom();

        UserLogin userLogin = new UserLogin();
        userLogin.setAccount(account);
        userLogin.setVerifyCode(verifyCode);

        // 2、获取数据库中已有的验证码
        Integer oldVerifyCode = userLoginMapper.queryVerifyCode(account);
        if (oldVerifyCode != null) {
            // 更新验证码
            userLogin.setUpdateUser(account);
            userLogin.setUpdateTime(new Date());
            userLoginMapper.updateLoginUser(userLogin);
        } else {
            // 插入新记录
            userLogin.setCreateUser(account);
            userLogin.setCreateTime(new Date());
            userLoginMapper.insertLoginUser(userLogin);
        }

        // 3、发送验证码到账号邮箱
        Email email = buildVerifyCodeEmail(account, verifyCode);
        emailService.sendMail(email);

        return verifyCode;
    }

    /**
     * 构建发送验证码的邮件
     *
     * @param account 登录账号(email地址)
     * @param verifyCode 验证码
     * @return Email对象
     */
    private Email buildVerifyCodeEmail(String account, int verifyCode) {
        Email email = new Email();
        email.getReceiverList().add(account);

        // 构建邮件标题
        StringBuilder titleBuilder = new StringBuilder();
        titleBuilder.append("[通知]").append(" 课程管理系统的登录验证码:").append(verifyCode);

        email.setTitle(titleBuilder.toString());
        return email;
    }
}
