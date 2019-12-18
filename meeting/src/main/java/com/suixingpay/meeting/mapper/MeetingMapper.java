package com.suixingpay.meeting.mapper;

import com.suixingpay.meeting.pojo.Meeting;

public interface MeetingMapper {

    Meeting queryMeetingByUserId(int userId);
}
