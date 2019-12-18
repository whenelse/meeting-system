package com.suixingpay.meeting.service.Impl;

import com.suixingpay.meeting.mapper.UserMapper;
import com.suixingpay.meeting.pojo.Result;
import com.suixingpay.meeting.pojo.User;
import com.suixingpay.meeting.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result userLogin(String userPhone) {
        Result result = new Result();
        try {
            User user = userMapper.selectUserLogin(userPhone);
            if (user == null) {
                result.set(200, "账号不存在", new User());
            }
            result.set(200, "登录成功", user);
        } catch (Exception e) {
            log.info("查询异常{ }", e);
            result.set(200, "登录异常", new User());
        }

        return result;
    }
}
