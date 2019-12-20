package com.suixingpay.meeting.service.Impl;

import com.suixingpay.meeting.mapper.UserMapper;
import com.suixingpay.meeting.pojo.Result;
import com.suixingpay.meeting.pojo.User;
import com.suixingpay.meeting.mapper.UserMapper;
import com.suixingpay.meeting.pojo.Meeting;
import com.suixingpay.meeting.pojo.Result;
import com.suixingpay.meeting.pojo.User;
import com.suixingpay.meeting.service.UserService;
import com.suixingpay.meeting.token.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
                result.set(200, "账号不存在", new Token());
            } else if (user.getUserId() == 1) {
                result.set(200, "管理员登录", user);
            } else {
                result.set(200, "鑫管家登录", user);
            }
        } catch (Exception e) {
            log.info("查询异常{ }", e);
            result.set(200, "登录异常", new Token());
        }
        return result;
    }

    @Autowired
    private UserMapper userMapper;
    @Override
    public Result checkUserHaveAuthority(int userId) {
        Result result = new Result();
        try {
            User user = userMapper.selectUserByUserId(userId);
            if (user != null) {
                if(user.getLevelNo() < 5) {
                    result.set(200, "您的鑫管家等级小于5，没有权限", null);
                } else {
                    result.set(200, "有权限", null);
                }
            } else {
                result.set(200, "用户不存在", null);
            }

        } catch (Exception e) {
            log.error("数据库查询异常：",e);
            result.set(200, "系统异常，请稍后", null);
        }
        return result;
    }
}
