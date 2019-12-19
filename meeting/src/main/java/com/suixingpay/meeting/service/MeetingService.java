package com.suixingpay.meeting.service;

import com.suixingpay.meeting.pojo.Meeting;
import com.suixingpay.meeting.pojo.Result;

public interface MeetingService {

    Result selectAll();

    Result auditPass(int meetingId);

    Result auditReject(int meetingId);

    Result queryMeetingByPUser(int userId);
}
