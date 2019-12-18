package com.suixingpay.meeting.service;

import com.suixingpay.meeting.pojo.Meeting;
import com.suixingpay.meeting.pojo.Result;

public interface MeetingService {

    Result selectAll();

    Result auditPass(Meeting meeting);

    Result auditReject(Meeting meeting);

}
