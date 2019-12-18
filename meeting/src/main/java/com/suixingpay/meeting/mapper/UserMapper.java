package com.suixingpay.meeting.mapper;

import com.suixingpay.meeting.pojo.User;

public interface UserMapper {

    User selectUserByUserId(int userId);
}
