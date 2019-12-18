package com.suixingpay.meeting.mapper;

import com.suixingpay.meeting.pojo.User;

public interface UserMapper {

    /**
     * @description  登录、通过手机号登录
     * @author Huang Yafeng
     * @date 2019/12/18 16:17
     * @param userPhone
     * @return
     */
    User selectUserLogin(String userPhone);
}
