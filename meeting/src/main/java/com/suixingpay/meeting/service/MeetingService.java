package com.suixingpay.meeting.service;

import com.suixingpay.meeting.pojo.Meeting;
import com.suixingpay.meeting.pojo.Result;


import com.suixingpay.meeting.pojo.Record;

public interface MeetingService {

    Result selectAll();

    Result auditPass(Meeting meeting);

    Result auditReject(Meeting meeting);

    Result queryMeetingByPUser(int userId);

    Result selectMeetingById(int meetingId);

    Result selectAllMeeting(Meeting meeting);

}
