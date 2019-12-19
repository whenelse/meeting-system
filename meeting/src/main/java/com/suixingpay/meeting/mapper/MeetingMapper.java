package com.suixingpay.meeting.mapper;

import com.suixingpay.meeting.pojo.Meeting;

import java.util.List;

public interface MeetingMapper {

    List<Meeting> queryMeetingByUserId(int meetingUserId);

    /**
     * 查看会议详情
     * @param meetingId
     * @return
     */
    Meeting selectMeetingById(int meetingId);

    List<Meeting> queryAllMeeting(Meeting meeting);
}
