package com.suixingpay.meeting.service;


import com.suixingpay.meeting.pojo.Record;

public interface MeetingService {
    Record signIn(Integer recordMeetingId, Integer recordUserId);
}
