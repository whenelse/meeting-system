package com.suixingpay.meeting.mapper;

import com.suixingpay.meeting.pojo.User;

public interface UserMapper {

    User selectUserByUserId(int userId);

    /**
     * 二维码签到
     * @param recordUserId
     * @return
     */
    User selectUserId(Integer recordUserId);
}
