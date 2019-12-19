package com.suixingpay.meeting.mapper;

import com.suixingpay.meeting.pojo.Meeting;
import org.springframework.stereotype.Component;

import java.util.List;

import com.suixingpay.meeting.pojo.Meeting;

public interface MeetingMapper {
    //测试，查询所有
    List<Meeting>  selectAll();
    //测试，修改会议
    Integer updateMeeting(Meeting meeting);

    List<Meeting> queryMeetingByUserId(int meetingUserId);

    /**
     * 查看会议详情
     * @param meetingId
     * @return
     */
    Meeting selectMeetingById(int meetingId);

    List<Meeting> queryAllMeeting(Meeting meeting);
    Meeting QRcode(Integer meetingId);
}
