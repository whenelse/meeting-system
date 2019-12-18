package com.suixingpay.meeting.service;

import com.suixingpay.meeting.pojo.Result;

public interface UserService {

    /**
     * @description 用户通过手机号登录
     * @author Huang Yafeng
     * @date 2019/12/18 16:23
     * @param
     * @return
     */
    Result userLogin(String userPhone);
}
