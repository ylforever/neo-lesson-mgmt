package com.neo.lesson.rest;

import com.elon.base.model.ResultModel;
import com.elon.base.rest.BaseController;
import com.neo.lesson.model.UserLogin;
import com.neo.lesson.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("/v1/login")
@Api(tags = "用户登录服务")
public class LoginController extends BaseController {
    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

    @Resource
    private LoginService loginService;

    /**
     * 用户登录
     *
     * @param loginUser 用户信息
     * @return 生成的token
     */
    @PostMapping("/login-in")
    @ApiOperation(value = "用户登录")
    public ResultModel<String> login(@RequestBody UserLogin loginUser){
        return loginService.login(loginUser);
    }

    /**
     * 生成校验码
     *
     * @param account 用户账号
     * @return 处理结果
     */
    @PostMapping("/generate-verify-code")
    @ApiOperation(value = "生成校验码")
    public ResultModel<String> generateVerifyCode(@RequestBody String account) {
        try {
            LOGGER.info("Invoke generateVerifyCode begin. account:{}", account);
            int verifyCode = loginService.generateVerifyCode(account);

            LOGGER.info("Invoke generateVerifyCode end. account:{}|verifyCode:{}", account, verifyCode);
            return ResultModel.success("Generate verify code success.");
        } catch (Exception e) {
            LOGGER.error("Invoke generateVerifyCode fail. account:{}", account, e);
            return ResultModel.success("Generate verify code fail.");
        }
    }
}
