package com.suixingpay.meeting.mapper;

import com.suixingpay.meeting.pojo.Meeting;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MeetingMapper {
    //测试，查询所有
    List<Meeting>  selectAll();
    //测试，修改会议
    Integer updateMeeting(Meeting meeting);

    Meeting queryMeetingByUserId(int userId);

}
