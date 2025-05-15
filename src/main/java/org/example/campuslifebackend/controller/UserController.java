package org.example.campuslifebackend.controller;

import jakarta.annotation.Resource;
import org.example.campuslifebackend.domain.dto.UserLoginDTO;
import org.example.campuslifebackend.domain.vo.UserLoginVO;
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
    public UserLoginVO login(@RequestBody UserLoginDTO userLoginDTO) {
        return userService.login(userLoginDTO);
    }
    
    /**
     * 绑定手机号
     * @param userNanoid 用户nanoid
     * @param phoneNumber 手机号
     * @param password 密码
     * @return 绑定结果
     */
    @PostMapping("/bindPhone")
    public boolean bindPhone(@RequestParam String userNanoid, 
                             @RequestParam String phoneNumber, 
                             @RequestParam String password) {
        return userService.bindPhone(userNanoid, phoneNumber, password);
    }
}
