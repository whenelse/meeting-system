package com.suixingpay.meeting.service.Impl;

import com.suixingpay.meeting.mapper.MeetingMapper;
import com.suixingpay.meeting.mapper.UserMapper;
import com.suixingpay.meeting.pojo.Meeting;
import com.suixingpay.meeting.pojo.Result;
import com.suixingpay.meeting.pojo.User;
import com.suixingpay.meeting.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MeetingServiceImpl implements MeetingService {

    @Autowired
    MeetingMapper meetingMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public Result queryMeetingByPUser(int userId) {
        Result result = new Result();
        List list = new ArrayList();
        User user = userMapper.selectUserByUserId(userId);
        Meeting meeting = meetingMapper.queryMeetingByUserId(userId);
        list.add(meeting);
        if (user.getRootUserId()!=user.getPUserId()){
            meeting = meetingMapper.queryMeetingByUserId(user.getUserId());
            list.add(meeting);
            user = userMapper.selectUserByUserId(user.getPUserId());
        }

        result.set(200,"查询成功",list);
        return result;
    }
}
