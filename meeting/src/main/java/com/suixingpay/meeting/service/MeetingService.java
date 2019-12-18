package com.suixingpay.meeting.service;

import com.suixingpay.meeting.pojo.Result;

public interface MeetingService {

    Result queryMeetingByPUser(int userId);
}
