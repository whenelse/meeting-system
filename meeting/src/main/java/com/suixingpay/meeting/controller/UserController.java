package com.suixingpay.meeting.controller;

import com.suixingpay.meeting.annotation.NoneAuth;
import com.suixingpay.meeting.pojo.Result;
import com.suixingpay.meeting.pojo.User;
import com.suixingpay.meeting.service.UserService;
import com.suixingpay.meeting.token.Token;
import com.suixingpay.meeting.token.TokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenHelper tokenHelper;

    @NoneAuth
    @RequestMapping("/login")
    public Result login(@RequestBody User user) {
        String userPhone1 =  user.getTelephone();
        Result result = userService.userLogin(userPhone1);
        if (result.getMsg().equals("账号不存在")) {
            return result;
        }
        User user1 = (User) result.getData();
        Token token = tokenHelper.create(user1);
        if (result.getMsg().equals("管理员登录")) {
            result.set(200, "管理员登录", token);
        }else {
            result.set(200, "鑫管家登录", token);
        }
        return result;
    }
}
