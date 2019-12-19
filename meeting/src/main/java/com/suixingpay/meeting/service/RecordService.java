package com.suixingpay.meeting.service;

import com.suixingpay.meeting.pojo.Meeting;
import com.suixingpay.meeting.pojo.Result;
import com.suixingpay.meeting.pojo.User;

public interface RecordService {
    //报名
    Result enroll(int userId, int meetingId);
}
