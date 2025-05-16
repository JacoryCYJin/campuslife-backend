package org.example.campuslifebackend.controller;

import jakarta.annotation.Resource;
import org.example.campuslifebackend.common.Result;
import org.example.campuslifebackend.common.enums.ResultEnum;
import org.example.campuslifebackend.domain.dto.UserLoginDTO;
import org.example.campuslifebackend.domain.vo.UserLoginVO;
import org.example.campuslifebackend.exception.BusinessException;
import org.example.campuslifebackend.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 用户登录
     * @param userLoginDTO 登录参数
     * @return 登录结果
     */
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        try {
            UserLoginVO loginVO = userService.login(userLoginDTO);
            return Result.success(loginVO);
        } catch (BusinessException e) {
            return Result.fail(e.getCode(), e.getMessage());
        } catch (Exception e) {
            return Result.fail(ResultEnum.SYSTEM_ERROR.getCode(), ResultEnum.SYSTEM_ERROR.getMessage());
        }
    }

    /**
     * 用户注册
     * @param userLoginDTO 注册参数
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result<UserLoginVO> register(@RequestBody UserLoginDTO userLoginDTO) {
        try {
            UserLoginVO registerVO = userService.register(userLoginDTO);
            return Result.success(registerVO);
        } catch (BusinessException e) {
            return Result.fail(e.getCode(), e.getMessage());
        } catch (Exception e) {
            return Result.fail(ResultEnum.SYSTEM_ERROR.getCode(), ResultEnum.SYSTEM_ERROR.getMessage());
        }
    }

    /**
     * 绑定手机号
     * @param userNanoid 用户nanoid
     * @param phoneNumber 手机号
     * @param password 密码
     * @return 绑定结果
     */
    @PostMapping("/bindPhone")
    public Result<Boolean> bindPhone(@RequestParam String userNanoid,
                                     @RequestParam String phoneNumber,
                                     @RequestParam String password) {
        try {
            boolean result = userService.bindPhone(userNanoid, phoneNumber, password);
            return Result.success(result);
        } catch (BusinessException e) {
            return Result.fail(e.getCode(), e.getMessage());
        } catch (Exception e) {
            return Result.fail(ResultEnum.SYSTEM_ERROR.getCode(), ResultEnum.SYSTEM_ERROR.getMessage());
        }
    }
}
